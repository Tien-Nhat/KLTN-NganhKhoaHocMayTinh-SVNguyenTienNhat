<template>
    <Navbar></Navbar>
    <header>
        <div class="bg-primary">
            <div class="container py-4">
                <!-- Breadcrumb -->
                <nav class="d-flex">
                    <h6 class="mb-0">
                        <a href="/" class="text-white-50">Home</a>
                        <span class="text-white-50 mx-2"> > </span>
                        <a :href="'/page-category-product/' + productInfo.category.id" class="text-white-50">{{
                            productInfo.category.name }}</a>
                        <span class="text-white-50 mx-2"> > </span>
                        <a :href="'/product/' + productInfo.id" class="text-white"><u>{{ productInfo.name }}</u></a>
                    </h6>
                </nav>
                <!-- Breadcrumb -->
            </div>
        </div>
    </header>
    <section class="py-5">
        <div class="container">
            <div class="row gx-5">
                <aside class="col-lg-6">
                    <div class="border rounded-4 mb-3 d-flex justify-content-center">
                        <a data-fslightbox="mygalley" class="rounded-4" target="_blank" data-type="image"
                            href="https://mdbcdn.b-cdn.net/img/bootstrap-ecommerce/items/detail1/big.webp">
                            <img style="max-width: 100%; max-height: 100vh; margin: auto;" class="rounded-4 fit"
                                :src="productInfo.image" />
                        </a>
                    </div>

                </aside>
                <main class="col-lg-6">
                    <div class="ps-lg-3">
                        <h4 class="title text-dark">
                            {{ productInfo.name }}
                        </h4>
                        <el-rate v-model="rating" :disabled="true" size="large" show-score text-color="#ff9900"
                            score-template="{value} sao" />
                        <div class="mb-3">
                            <span class="h5">{{ formatCurrency2(productInfo.price) }}</span>
                            <span class="text-muted">/mỗi cái</span>
                        </div>

                        <p>
                            {{ productInfo.description }}
                        </p>

                        <div class="row">
                            <dt class="col-3">Loại:</dt>
                            <a :href="'/page-category-product/' + productInfo.category.id" id="type" class="col-9">{{
                                productInfo.category.name }}</a>
                        </div>

                        <hr />

                        <div class="row mb-4">
                            <div class="col-md-4 col-6 mb-3">
                                <label class="mb-2 d-block">Số lượng</label>
                                <div class="input-group mb-3" style="width: 170px;">
                                    <button @click="(quatity > 1) ? (quatity = quatity - 1) : (quatity = 1)"
                                        class="btn btn-white border border-secondary px-3" type="button"
                                        id="button-addon1" data-mdb-ripple-color="dark">
                                        <i class="fas fa-minus"></i>
                                    </button>
                                    <input type="text" class="form-control text-center border border-secondary"
                                        v-model="quatity" aria-label="Example text with button addon"
                                        aria-describedby="button-addon1" />
                                    <button @click="quatity = quatity + 1"
                                        class="btn btn-white border border-secondary px-3" type="button"
                                        id="button-addon2" data-mdb-ripple-color="dark">
                                        <i class="fas fa-plus"></i>
                                    </button>
                                </div>
                            </div>
                        </div>
                        <a @click="addCartItem" href="/cart" class="btn btn-warning shadow-0 m-1"> Mua ngay </a>
                        <div @click="addCartItem" class="btn btn-primary shadow-0 m-1 "> <i
                                class="me-1 fa fa-shopping-basket"></i>
                            Thêm vào giỏ </div>
                    </div>
                </main>
            </div>
        </div>
    </section>
    <div v-if="isBuy" class="container mt-5">
        <div class="card align-items-center">
            <h4 class="title text-dark">
                Đánh giá của bạn với sản phẩm
            </h4>
            <el-rate v-model="yourRating" size="large" clearable />
        </div>
    </div>
    <section>
        <div class="container my-5">
            <header class="mb-4">
                <h3>Sản phẩm tương tự</h3>
            </header>

            <div class="row">
                <div v-for="(item, index) in similarProduct" :key="index" class="col-lg-3 col-md-6 col-sm-6">
                    <div class="card px-4 border shadow-0 mb-4 mb-lg-0">
                        <div class="mask px-2" style="height: 50px;">
                            <div class="d-flex justify-content-between">
                                <h6><span class="badge bg-danger pt-1 mt-3 ms-2">Mới</span></h6>
                                <a href="#"><i class="fas fa-heart text-primary fa-lg float-end pt-3 m-2"></i></a>
                            </div>
                        </div>
                        <a :href="'/product/' + item.id" class="">
                            <el-image class="card-img-top rounded-2" style="width: 256px; height: 256px"
                                :src="item.image" fit="cover" />
                        </a>
                        <div class="card-body d-flex flex-column pt-3 border-top">
                            <a :href="'/product/' + item.id" class="nav-link">{{ item.name }}</a>
                            <div class="price-wrap mb-2">
                                <strong class="">{{ formatCurrency2(item.price) }}</strong>
                            </div>
                            <div class="card-footer d-flex align-items-end pt-3 px-0 pb-0 mt-auto">
                                <div @click="addCartItem(item.id)" class="btn btn-outline-primary w-100">Thêm vào giỏ
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </section>

</template>
<script>
import Navbar from '@/components/Navbar/Navbar.vue';
import ProductService from '@/services/product.service';
import CartService from '@/services/cart.service';
import { ElMessage } from 'element-plus';
import ListProduct from '@/components/ListProduct/ListProduct.vue';
import reviewService from '@/services/review.service';
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
            productInfo: {
                category: {},
                name: null,
                price: 0,
                description: null

            },
            quatity: 1,
            similarProduct: [],
            rating: 0,
            isBuy: false,
            yourRating: 0
        }
    },
    watch: {
        yourRating: function (newValue, oldValue) {
            this.addRating();
        }
    },
    mounted() {
        this.getProduct();
        this.getSimilarProduct();
        this.getRating();
        this.isBuying();
    },
    methods: {
        async addRating() {
            var reviewRequest = {
                userId: this.$store.state.auth.user.id,
                productId: this.$route.params.id,
                rating: this.yourRating
            }
            await reviewService.addRating(reviewRequest).then(
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

                    ElMessage.error(this.response.message);
                }
            );
            if (this.response.status != "SUCCESS") {
                ElMessage.error(this.response.message);
            }

        },
        async isBuying() {

            await reviewService.isBuy(this.$route.params.id, this.$store.state.auth.user.id).then(
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

                    ElMessage.error(this.response.message);
                }
            );
            if (this.response.status == "SUCCESS") {
                this.isBuy = this.response.result.isBuy;
                this.yourRating = this.response.result.yourRating;
            }
            else {
                ElMessage.error(this.response.message);
            };
        },
        async getRating() {

            await reviewService.getRating(this.$route.params.id).then(
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

                    ElMessage.error(this.response.message);
                }
            );
            if (this.response.status == "SUCCESS") {
                this.rating = this.response.result;
            }
            else {
                ElMessage.error(this.response.message);
            };
        },
        async getSimilarProduct() {

            await ProductService.getSimiProduct(this.$route.params.id).then(
                (response) => {
                    this.response = response.data;
                    this.similarProduct = this.response;
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
        async addCartItem(productId = null) {

            const cartItem = {
                userId: this.$store.state.auth.user.id,
                productId: (productId == null) ? this.$route.params.id : productId,
                quantity: this.quatity
            }
            await CartService.addCartItem(cartItem).then(
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
                ElMessage({
                    message: this.response.message,
                    type: 'success',
                });
                $('#exampleModalAdd').modal('toggle');
            }
            else {
                ElMessage.error(this.response.message);
            };
        },
        async addCartItem() {
            const cartItem = {
                userId: this.$store.state.auth.user.id,
                productId: this.$route.params.id,
                quantity: this.quatity
            }
            await CartService.addCartItem(cartItem).then(
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
                ElMessage({
                    message: this.response.message,
                    type: 'success',
                });
                $('#exampleModalAdd').modal('toggle');
            }
            else {
                ElMessage.error(this.response.message);
            };
        },
        async getProduct() {

            await ProductService.getProductInfo(this.$route.params.id).then(
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
                this.productInfo = this.response.result;
            }
            else {
                ElMessage.error(this.response.message);
            };
        },
        formatCurrency2(amount) {
            return amount.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
        }
    }
}
</script>

<style scoped>
@import url(./DetailProduct.css);
@import url(https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css);
@import url(https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css);
</style>