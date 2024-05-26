<template>
    <Navbar></Navbar>
    <div class="modal fade" id="exampleModalAdd" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg" style="width: 700px;">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Thêm sản phẩm</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form @submit.prevent="addProduct">
                        <div class="form-group mb-3">
                            <label for="exampleFormControl1">Danh mục</label>
                            <select class="form-select" id="exampleFormControl1" v-model="product.categoryId"
                                aria-label="Default select example" required>
                                <option v-for="(item, index) in listCategory" :key="item" :value="item.id">
                                    {{ item.name }}
                                </option>
                            </select>
                        </div>
                        <div class="form-group mb-3">
                            <label for="exampleFormControl2">Tên</label>
                            <input type="text" v-model="product.name" id="exampleFormControl2" class="form-control"
                                required>
                        </div>
                        <label for="exampleFormControl3">Giá tiền</label>
                        <div class="input-group mb-3">
                            <input v-model="product.price" type="number" id="exampleFormControl3" class="form-control"
                                aria-label="Amount (to the nearest dollar)" required>
                            <span class="input-group-text">VNĐ</span>
                        </div>
                        <div class="mb-3">
                            <label for="formFileSm" class="form-label">Ảnh sản phẩm</label>
                            <input class="form-control form-control-sm" accept="image/*" @change="handleFileChange"
                                id="formFileSm" type="file">
                        </div>
                        <div class="form-group mb-3">
                            <label for="exampleFormControl4">Mô tả</label>
                            <textarea class="form-control rounded-0 shadow-none" style="height: 300px;"
                                id="exampleFormControl4" placeholder="Viết mô tả sản phẩm ở đây." rows="5"
                                v-model="product.description" required></textarea>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                    <button type="button" @click="addProduct" class="btn btn-primary">Thêm</button>
                </div>
            </div>
        </div>
    </div>
    <div class="container">
        <div class="row">
            <div class="container my-5">
                <header class="d-sm-flex align-items-center justify-content-between border-bottom mb-4 pb-3">
                    <h4 style="margin-bottom: -9px;"><strong class="d-block py-2">Sản phẩm của bạn </strong></h4>
                    <a href="#!" style="margin-bottom: -9px;" class="btn btn-primary shadow-0 me-1"
                        @click="callAddProduct"><i class="fa fa-plus"></i>Thêm sản phẩm</a>
                </header>

                <div class="row">
                    <div v-for="(item, index) in listProduct" :key="item" class="col-lg-3 col-md-6 col-sm-6">
                        <div class="card w-100 my-2 shadow-2-strong">
                            <button type="button" @click="deleteProduct(item.id)" id="close-product" class="btn-close"
                                aria-label="Close"></button>
                            <el-image style="width: 304; height: 304px" :src="item.image" fit="cover" />
                            <div class="card-body d-flex flex-column">
                                <div class="d-flex flex-row">
                                    <h5 class="mb-1 me-1">{{ formatCurrency(item.price) }}</h5>
                                    <span class="text-danger"><s>{{ formatCurrency(item.price + 10000) }}</s></span>
                                </div>
                                <p class="card-text">{{ item.name }}</p>
                                <div class="card-footer d-flex align-items-end pt-3 px-0 pb-0 mt-auto">
                                    <a href="#!" class="btn btn-primary shadow-0 me-1">Thêm vào giỏ hàng</a>
                                    <a href="#!" class="btn btn-light border icon-hover px-2 pt-2"><i
                                            class="fas fa-heart fa-lg text-secondary px-1"></i></a>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>

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
import CategoryService from '@/services/category.service';
import ProductService from '@/services/product.service';
import { ElMessage, ElMessageBox } from 'element-plus'

export default {
    name: 'TimeLine',
    components: {
        Navbar,
    },
    data() {
        return {
            listCategory: [],
            product: {
                categoryId: null,
                name: null,
                price: null,
                description: null

            },
            productClone: {
                categoryId: null,
                name: null,
                price: null,
                description: null

            },
            listProduct: [],
            pageNumber: 0,
            pageSize: 8,
            image: null,
            paginationInfo: {}
        }
    },
    mounted() {
        this.getListCategory();
        this.getProductsUser();
    },
    computed: {
        isProductIncomplete() {
            return Object.values(this.product).some(value => value === null);
        }
    },
    methods: {
        changePage(val) {
            this.pageNumber = val - 1;
            this.getProductsUser();
        },
        formatCurrency(amount) {
            return amount.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
        },
        callAddProduct() {
            $('#exampleModalAdd').modal('toggle');
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
        handleFileChange(event) {
            this.image = event.target.files[0];
        },
        async addProduct() {
            this.product.sellerId = this.$store.state.auth.user.id;
            if (!this.isProductIncomplete && this.image != null) {
                const formData = new FormData();
                formData.append('image', this.image);
                formData.append('product', JSON.stringify(this.product));
                await ProductService.addProduct(formData).then(
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
                    this.product = { ...this.productClone };
                    this.image = null;
                    this.getProductsUser();
                    document.getElementById('formFileSm').value = "";
                    ElMessage({
                        message: this.response.message,
                        type: 'success',
                    });
                    $('#exampleModalAdd').modal('toggle');
                }
                else {
                    ElMessage.error(this.response.message);
                };
            }
            else {
                ElMessage({
                    message: 'Không được để trống',
                    type: 'warning',
                })
            }
        },
        async getProductsUser() {

            await ProductService.getProductsUser(this.pageNumber, this.pageSize, this.$store.state.auth.user.id).then(
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
        },
        async deleteProduct(productId) {
            ElMessageBox.confirm(
                'Bạn có muốn xóa sản phẩm này không',
                'Warning',
                {
                    confirmButtonText: 'Có',
                    cancelButtonText: 'Hủy',
                    type: 'warning',
                })
                .then(async () => {
                    await ProductService.deleteProduct(productId).then(
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
                        this.getProductsUser();
                        ElMessage({
                            message: this.response.message,
                            type: 'success',
                        });
                    }
                    else {
                        ElMessage.error(this.response.message);
                    };
                })
                .catch(() => {
                    ElMessage({
                        type: 'info',
                        message: 'Đã hủy',
                    })
                }
                )

        },
    }
}
</script>

<style scoped>
@import url(./AddProduct.css);
@import url(https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css);
@import url(https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css);
</style>