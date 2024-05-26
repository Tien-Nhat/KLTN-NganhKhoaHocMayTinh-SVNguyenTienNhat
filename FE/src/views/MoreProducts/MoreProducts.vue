<template>
    <Navbar></Navbar>

    <div class="container">
        <div class="row">
            <div class="container my-5">
                <header class="d-sm-flex align-items-center border-bottom mb-4 pb-3">
                    <h4 style="margin-bottom: -9px;"><strong class="d-block py-2">Danh sách sản phẩm </strong></h4>
                </header>
                <ListProduct :listProduct="listProduct"></ListProduct>

                <hr />

                <!-- Pagination -->
                <nav aria-label="Page navigation example" class="d-flex justify-content-center mt-3">
                    <el-pagination background layout="prev, pager, next" :hide-on-single-page="true"
                        :page-size="pageSize" :total="this.paginationInfo.totalElements" @current-change="changePage" />
                </nav>
                <!-- Pagination -->
            </div>
        </div>
    </div>

</template>
<script>
import Navbar from '@/components/Navbar/Navbar.vue';
import "https://code.jquery.com/jquery-1.10.2.min.js";
import ProductService from '@/services/product.service';
import { ElMessage } from 'element-plus';
import ListProduct from '@/components/ListProduct/ListProduct.vue';

export default {
    name: 'TimeLine',
    components: {
        Navbar,
        ListProduct
    },
    data() {
        return {
            listCategory: [],
            product: {},
            listProduct: [],
            pageNumber: 0,
            pageSize: 8,
            image: null,
            paginationInfo: {}
        }
    },
    mounted() {
        this.getProducts();
    },
    methods: {
        changePage(val) {
            this.pageNumber = val - 1;
            this.getProducts();
        },
        formatCurrency(amount) {
            return amount.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
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
                this.paginationInfo = this.response.result;
                this.listProduct = this.response.result.content;
            }
            else {
                ElMessage.error(this.response.message);
            };
        }
    }
}
</script>

<style scoped>
@import url(./MoreProducts.css);
@import url(https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css);
@import url(https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css);
</style>