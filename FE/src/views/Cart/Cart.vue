<template>
    <Navbar></Navbar>
    <header>
        <!-- Heading -->
        <div class="bg-primary">
            <div class="container py-4">
                <!-- Breadcrumb -->
                <nav class="d-flex">
                    <h6 class="mb-0">
                        <a href="/" class="text-white-50">Home</a>
                        <span class="text-white-50 mx-2"> > </span>
                        <a href="" class="text-white"><u>Shopping cart</u></a>
                    </h6>
                </nav>
                <!-- Breadcrumb -->
            </div>
        </div>
        <!-- Heading -->
    </header>

    <!-- cart + summary -->
    <section class="bg-light my-5">
        <div class="container">
            <div class="row">
                <!-- cart -->
                <div class="col-lg-9">
                    <div class="card border shadow-0">
                        <div class="m-4">
                            <h4 class="card-title mb-4">Giỏ hàng của bạn</h4>
                            <div v-for="(item, index) in listCartItem" :key="index" class="row gy-3 mb-4">
                                <div class="col-lg-5">
                                    <div class="me-lg-5">
                                        <div class="d-flex">
                                            <img :src="item.image" class="border rounded me-3"
                                                style="width: 96px; height: 96px;" />
                                            <div class="">
                                                <a :href="'/product/' + item.productId" class="nav-link">{{ item.name
                                                    }}</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div
                                    class="col-lg-2 col-sm-6 col-6 d-flex flex-row flex-lg-column flex-xl-row text-nowrap">
                                    <div class="">
                                        <div class="input-group me-4" style="width: 170px;">
                                            <button
                                                @click="(item.quantity > 1) ? (item.quantity = item.quantity - 1) : (item.quantity = 1)"
                                                class="btn btn-white border border-secondary px-3" type="button"
                                                id="button-addon1" data-mdb-ripple-color="dark">
                                                <i class="fas fa-minus"></i>
                                            </button>
                                            <input type="text" class="form-control text-center border border-secondary"
                                                v-model="item.quantity" aria-label="Example text with button addon"
                                                aria-describedby="button-addon1" />
                                            <button @click="item.quantity = item.quantity + 1"
                                                class="btn btn-white border border-secondary px-3" type="button"
                                                id="button-addon2" data-mdb-ripple-color="dark">
                                                <i class="fas fa-plus"></i>
                                            </button>
                                        </div>
                                    </div>
                                    <div class="">
                                        <text class="h6">{{ formatCurrency(item.price * item.quantity) }}</text> <br />
                                        <small class="text-muted text-nowrap"> {{ formatCurrency(item.price) }} / 1
                                            sản phẩm </small>
                                    </div>
                                </div>
                                <div
                                    class="col-lg col-sm-6 d-flex justify-content-sm-center justify-content-md-start justify-content-lg-center justify-content-xl-end mb-2">
                                    <div class="float-md-end">
                                        <a href="#!" class="btn btn-light border px-2 icon-hover-primary"><i
                                                class="fas fa-heart fa-lg px-1 text-secondary"></i></a>
                                        <button @click="removeCartItem(item.id)"
                                            class="btn btn-light border text-danger icon-hover-danger">
                                            Loại bỏ</button>
                                    </div>
                                </div>
                            </div>


                        </div>

                        <div class="border-top pt-4 mx-4 mb-4">
                            <p><i class="fas fa-truck text-muted fa-lg"></i> Giao hàng miễn phí trong vòng 1-2 tuần</p>
                            <p class="text-muted">
                                Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor
                                incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud
                                exercitation ullamco laboris nisi ut
                                aliquip
                            </p>
                        </div>
                    </div>
                </div>
                <!-- cart -->
                <!-- summary -->
                <div class="col-lg-3">

                    <div class="card shadow-0 border">
                        <div class="card-body">
                            <div class="d-flex justify-content-between">
                                <p class="mb-2">Tổng giá:</p>
                                <p class="mb-2">{{ formatCurrency(totalPrice) }}</p>
                            </div>
                            <div class="d-flex justify-content-between">
                                <p class="mb-2">Giảm giá:</p>
                                <p class="mb-2 text-success">{{ formatCurrency(0) }}</p>
                            </div>
                            <hr />
                            <div class="d-flex justify-content-between">
                                <p class="mb-2">Tổng giá:</p>
                                <p class="mb-2 fw-bold">{{ formatCurrency(totalPrice) }}</p>
                            </div>

                            <div class="mt-3">
                                <div @click="addOrder" class="btn btn-success w-100 shadow-0 mb-2"> Mua hàng </div>
                                <a href="/" class="btn btn-light w-100 border mt-2"> Trở về trang chủ </a>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- summary -->
            </div>
        </div>
    </section>


</template>
<script>
import Navbar from '@/components/Navbar/Navbar.vue';
import cartService from '@/services/cart.service';
import { ElMessage } from 'element-plus';
import ListProduct from '@/components/ListProduct/ListProduct.vue';
import orderService from '@/services/order.service';


export default {
    beforeRouteLeave(to, from, next) {
        if (this.formChanged) {
            const result = confirm('You have unsaved changes. Are you sure you want to leave?')
            if (result) {
                next()
            } else {
                next(false)
            }
        } else {
            next()
        }
    },
    name: 'Cart',
    components: {
        Navbar,
        ListProduct
    },
    data() {
        return {
            pageNumber: 0,
            pageSize: 8,
            listCartItem: []
        }
    },
    computed: {
        totalPrice() {
            let total = 0;
            this.listCartItem.forEach(product => {
                total += product.price * product.quantity;
            });
            return total;
        }
    },
    mounted() {
        this.getCartItem();
    },
    methods: {
        async addOrder() {
            await orderService.addOrder(this.$store.state.auth.user.id).then(
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
                this.getCartItem();
            }
            else {
                ElMessage.error(this.response.message);
            };
        },
        async removeCartItem(cartItemId) {

            await cartService.removeCartItem(cartItemId).then(
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
                this.getCartItem();
            }
            else {
                ElMessage.error(this.response.message);
            };
        },
        async getCartItem() {

            await cartService.getCartItem(this.$store.state.auth.user.id).then(
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
                this.listCartItem = this.response.result;
            }
            else {
                ElMessage.error(this.response.message);
            };
        },
        formatCurrency(amount) {
            return amount.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
        }
    }

}
</script>

<style scoped>
@import url(./Cart.css);
@import url(https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css);
@import url(https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css);
</style>