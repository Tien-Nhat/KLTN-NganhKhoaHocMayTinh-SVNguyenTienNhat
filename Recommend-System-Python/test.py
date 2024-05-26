from sklearn.metrics.pairwise import cosine_similarity, linear_kernel
import numpy as np

# Tạo dữ liệu mẫu
vector_a = np.array([1, 2, 3])
vector_b = np.array([4, 5, 6])

# Tính toán cosine similarity
cos_sim = cosine_similarity([vector_a], [vector_b])
print(f'Cosine Similarity: {cos_sim[0][0]}')

# Tính toán linear kernel
lin_kernel = linear_kernel([vector_a], [vector_b])
print(f'Linear Kernel: {lin_kernel[0][0]}')
