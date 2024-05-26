from sqlalchemy import create_engine
import pandas as pd
import re
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
from pyvi import ViTokenizer
from scipy.sparse import csr_matrix


from flask import Flask, request, jsonify
from flask_cors import CORS
app = Flask(__name__)
CORS(app)

# Endpoint cho khuyến nghị sản phẩm
@app.route('/recommend', methods=['GET'])
def recommend_CB():
    # Kết nối đến cơ sở dữ liệu MySQL
    db_connection_str = 'mysql://root:123456@localhost/khoaluan'
    db_connection = create_engine(db_connection_str)

    # Lấy dữ liệu từ bảng transactions
    query = """
        SELECT p.id, p.name, p.image, p.price, p.description, c.name AS category_name
        FROM product p
        JOIN category c ON p.category_id = c.id
        WHERE p.deleted = false
    """
    transactions = pd.read_sql(query, db_connection)

    db_connection.dispose()
    # Hàm loại bỏ stopword và tiền xử lý văn bản
    def preprocess_text_vietnamese(text):
        if text is None:
            return ""
        # Loại bỏ ký tự xuống dòng
        text = text.replace('\n', ' ').replace('\r', ' ')
        # Chuyển đổi thành chữ thường
        text = text.lower()
        # Loại bỏ ký tự đặc biệt và số
        text = re.sub(r'(?<=\d)\.(?=\d)', 'DOT_PLACEHOLDER', text)  # Thay dấu chấm giữa các số bằng placeholder
        words = ViTokenizer.tokenize(text).split()

        # Tách từ và loại bỏ stopword
        
        # filtered_words = [word for word in words if word not in stopwords]
        # Ghép các từ lại thành câu
        text = ' '.join(words)
        text = re.sub(r'[^\w\s]', ' ', text)
        text = re.sub(r'DOT_PLACEHOLDER', '.', text)
        text = re.sub(r'\s+', ' ', text).strip()
        # Loại bỏ khoảng trắng dư thừa
        text = text.strip()
        return text



    def get_recommendations(product_id, cosine_sim,transactions):
        product_index=transactions[transactions['id'] == product_id].index[0]

        # Lấy các điểm tương đồng của sản phẩm được chọn với tất cả các sản phẩm khác
        sim_scores = list(enumerate(cosine_sim[product_index]))

        # Sắp xếp các sản phẩm theo độ tương đồng giảm dần
        sim_scores = sorted(sim_scores, key=lambda x: x[1], reverse=True)
        sim_scores = [score for score in sim_scores if score[1] > 0.18]
        # Lấy các chỉ số của các sản phẩm tương tự nhất
        sim_scores = sim_scores[1:5]

        # Lấy các chỉ số sản phẩm
        product_indices = [i[0] for i in sim_scores]
        similarities = [i[1] for i in sim_scores]

        # Trả về các sản phẩm tương tự cùng với độ tương đồng
        recommended_products = transactions.iloc[product_indices].copy()
        recommended_products['similarity'] = similarities

        # Trả về các sản phẩm tương tự
        return recommended_products

    transactions['full_description'] = transactions.apply(lambda row: f"{row['category_name']} {row['name']}", axis=1)

    # Áp dụng hàm chuẩn hóa cho cột description trong transactions
    transactions['full_description'] = transactions['full_description'].apply(preprocess_text_vietnamese)


    # Tạo một TfidfVectorizer object
    vectorizer = TfidfVectorizer()

    # Tính toán TF-IDF cho mô tả sản phẩm
    tfidf_matrix = vectorizer.fit_transform(transactions['full_description'])

    cosine_sim = cosine_similarity(tfidf_matrix, tfidf_matrix)

    # Lấy chỉ số sản phẩm từ tham số query
    product_id = int(request.args.get('product_id'))

    # Lấy danh sách khuyến nghị
    recommended_products = get_recommendations(product_id,cosine_sim,transactions)

    # Chuyển kết quả thành dictionary để trả về JSON
    result = recommended_products.to_dict(orient='records')

    return jsonify(result)

@app.route('/recommendations', methods=['GET'])
def recommend_CF():
     # Kết nối đến cơ sở dữ liệu MySQL
    db_connection_str = 'mysql://root:123456@localhost/khoaluan'
    db_connection = create_engine(db_connection_str)

    # Lấy dữ liệu từ bảng transactions
    query = "SELECT user_id, product_id,rating FROM reviews "
    transactions = pd.read_sql(query, db_connection)
    # Lấy dữ liệu từ bảng product
    query_products = "SELECT id, name, image, price FROM product"
    products = pd.read_sql(query_products, db_connection)

    db_connection.dispose()

    # Tạo ma trận người dùng - sản phẩm
    interaction_matrix = transactions.pivot_table(index='product_id', columns='user_id', values='rating', aggfunc='sum')
    # Chuẩn hóa ma trận tương tác bằng cách trừ giá trị trung bình của từng cột
    interaction_matrix_normalized = interaction_matrix - interaction_matrix.mean(axis=0)

    user_id = int(request.args.get('user_id'))
    if user_id not in interaction_matrix_normalized.columns :
        return jsonify({"isHave": False, "result": []})

    interaction_matrix_normalized = interaction_matrix_normalized.fillna(0)

    # Chuyển đổi ma trận thành dạng thưa (sparse matrix)
    interaction_matrix_sparse = csr_matrix(interaction_matrix_normalized.values)

    # Tính toán độ tương đồng giữa các người dùng
    user_similarity = cosine_similarity(interaction_matrix_sparse.T)
    user_similarity_df = pd.DataFrame(user_similarity, index=interaction_matrix.columns, columns=interaction_matrix.columns)

    def predict(user_id, product_id, user_product_matrix, user_similarity_df,k=2):
        # Lấy các người dùng tương tự và bỏ đi user_id hiện tại
        similar_users = user_similarity_df[user_id].drop(user_id).sort_values(ascending=False)
        # Chỉ giữ lại những người dùng đã đánh giá sản phẩm product_id
        similar_users = similar_users[similar_users.index.isin(user_product_matrix.columns[interaction_matrix.loc[product_id].notna()])]
        # Chỉ chọn k người dùng tương tự nhất
        top_k_similar_users = similar_users.head(k)
        similar_users_ratings = user_product_matrix.loc[product_id,top_k_similar_users.index]
        total_similarity = similar_users.loc[similar_users_ratings.index].abs().sum()
        if total_similarity == 0:
         return 0  # Trả về 0 nếu total_similarity bằng 0
        predicted_rating = similar_users_ratings.dot(similar_users.loc[similar_users_ratings.index]) / total_similarity
        return predicted_rating
    
    def recommend_products(user_id, user_product_matrix, user_similarity_df, k=2, top_n=5):
        # Lấy tất cả các sản phẩm mà user_id chưa đánh giá
        user_ratings = interaction_matrix[user_id]
        products_not_rated = user_ratings[user_ratings.isna()].index
        
        predicted_ratings = {}
        for product_id in products_not_rated:
            predicted_rating = predict(user_id, product_id, user_product_matrix, user_similarity_df, k)
            if predicted_rating > 0:  # Chỉ lấy những dự đoán rating lớn hơn 0
                predicted_ratings[product_id] = predicted_rating
        
        # Sắp xếp các sản phẩm theo rating dự đoán giảm dần
        sorted_predicted_ratings = sorted(predicted_ratings.items(), key=lambda x: x[1], reverse=True)
        
        # Lấy top_n sản phẩm có rating dự đoán cao nhất
        top_n_recommendations = sorted_predicted_ratings[:top_n]
        
        # Trả về thông tin sản phẩm
        recommendations = []
        for product_id, predicted_rating in top_n_recommendations:
            product_info = products[products['id'] == product_id].iloc[0].to_dict()
            product_info['predicted_rating'] = predicted_rating
            recommendations.append(product_info)
        
        return recommendations
    
    if len(recommend_products(user_id, interaction_matrix_normalized, user_similarity_df, k=2, top_n=8))==0:
        return jsonify({"isHave": False, "result": []})
    return jsonify({"isHave": True, "result": recommend_products(user_id, interaction_matrix_normalized, user_similarity_df, k=2, top_n=8)})



if __name__ == '__main__':
    app.run(debug=True)
