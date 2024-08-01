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
                            <h4 class="card-title mb-4">Đơn hàng của bạn</h4>
                            <div v-for="(item, index) in listOrder" :key="index">
                                <header class="d-sm-flex align-items-center border-bottom mb-4 pb-3">
                                    <h5 style="margin-bottom: -9px;">
                                        <div class="d-block py-2">Mã đơn hàng: {{ item.orderId }} </div>
                                    </h5>
                                </header>
                                <div v-for="(item2, index2) in item.orderItem" :key="index2" class="row gy-3 mb-4">
                                    <div class="col-lg-5">
                                        <div class="me-lg-5">
                                            <div class="d-flex">
                                                <img :src="item2.image" class="border rounded me-3"
                                                    style="width: 96px; height: 96px;" />
                                                <div class="">
                                                    <a :href="'/product/' + item2.productId" class="nav-link">{{
                                                        item2.name
                                                        }}</a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div
                                        class="col-lg-2 col-sm-6 col-6 d-flex flex-row flex-lg-column flex-xl-row text-nowrap">
                                        <div class="">
                                            <text class="h6 me-4">Số lượng: {{ item2.quantity }}</text>

                                        </div>
                                        <div class="">
                                            <text class="h6">{{ formatCurrency(item2.price * item2.quantity) }}</text>
                                            <br />
                                            <small class="text-muted text-nowrap"> {{ formatCurrency(item2.price) }} / 1
                                                sản phẩm </small>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>


                    </div>
                </div>
                <!-- cart -->

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
            listOrder: [],
        }
    },
    mounted() {
        this.getOrder();
    },
    methods: {
        async getOrder() {

            await orderService.getOrder(this.$store.state.auth.user.id).then(
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
                this.listOrder = this.response.result;
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
@import url(./Order.css);
@import url(https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css);
@import url(https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css);
</style>