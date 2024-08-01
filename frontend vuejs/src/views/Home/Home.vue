<template>
    <Navbar></Navbar>
    <div class="container">
        <div class="row">
            <div class="container my-5">
                <header class="d-sm-flex align-items-center border-bottom mb-4 pb-3">
                    <h4 style="margin-bottom: -9px;"><strong class="d-block py-2">Danh mục </strong></h4>
                </header>
                <div class="row">
                    <div v-for="(item, index) in listCategory" :key="item" class="col col-lg-2 p-0">
                        <a :href="'/page-category-product/' + item.id"
                            class="card w-100 align-items-center shadow-2-strong"
                            style="border-radius: 0; text-decoration: none; color: black; height: 126px; ">
                            <el-avatar :size="80" fit="cover" class="m-2" :src="item.image" />
                            <p class="mb-1 me-1">{{ item.name }}</p>
                        </a>
                    </div>
                </div>
            </div>
            <div v-if="recommendCF.isHave" class="container my-2">
                <header class="d-sm-flex align-items-center border-bottom mb-4 pb-3">
                    <h4 style="margin-bottom: -9px;"><strong class="d-block py-2">Gợi ý sản phẩm dành cho bạn </strong>
                    </h4>
                </header>
                <ListProduct :listProduct="recommendCF.result"></ListProduct>
            </div>
            <div class="container my-2">
                <header class="d-sm-flex align-items-center border-bottom mb-4 pb-3">
                    <h4 style="margin-bottom: -9px;"><strong class="d-block py-2">Sản phẩm </strong></h4>
                </header>
                <ListProduct :listProduct="listProduct"></ListProduct>
                <div class="position">
                    <hr class="line">
                    <a href="/page-product" class="btn btn-link custom-button">Xem thêm</a>
                </div>

            </div>
        </div>
    </div>

</template>
<script>
import Navbar from '@/components/Navbar/Navbar.vue';
import CategoryService from '@/services/category.service';
import ProductService from '@/services/product.service';
import { ElMessage } from 'element-plus';
import ListProduct from '@/components/ListProduct/ListProduct.vue';
import productService from '@/services/product.service';
export default {
    name: 'TimeLine',
    components: {
        Navbar,
        ListProduct
    },
    data() {
        return {
            pageNumber: 0,
            pageSize: 8,
            listProduct: [],
            listCategory: [],
            recommendCF: {
                isHave: false,
                result: []
            }
        }
    },
    mounted() {
        this.getProducts();
        this.getListCategory();
        this.getRecommendProduct();
    },
    methods: {
        async getRecommendProduct() {

            await productService.getRecommendCF(this.$store.state.auth.user.id).then(
                (response) => {
                    this.response = response.data;
                    this.recommendCF = this.response;
                },
                (error) => {
                    this.response =
                        (error.response &&
                            error.response.data &&
                            error.response.data.message) ||
                        error.message ||
                        error.toString();

                    ElMessage.error(this.response.message);
                }
            );
        },
        async getProducts() {

            await ProductService.getProducts(this.pageNumber, this.pageSize).then(
                (response) => {
                    this.response = response.data;
                },
                (error) => {
                    this.response =
                        (error.response &&
                            error.response.data &&
                            error.response.data.message) ||
                        error.message ||
                        error.toString();
                }
            );

            if (this.response.status == "SUCCESS") {
                this.listProduct = this.response.result.content;
            }
            else {
                ElMessage.error(this.response.message);
            };
        },
        async getListCategory() {
            await CategoryService.getCategory().then(
                (response) => {
                    this.response = response.data;
                },
                (error) => {
                    this.response =
                        error.response

                }
            );

            if (this.response.status == "SUCCESS") {
                this.listCategory = this.response.result;
            }

        },
    }
}
</script>

<style scoped>
@import url(./Home.css);
@import url(https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css);
@import url(https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css);
</style>