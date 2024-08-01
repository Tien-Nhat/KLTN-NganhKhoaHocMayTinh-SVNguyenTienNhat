<template>
    <div class="main-navbar shadow-sm sticky-top">
        <div class="top-navbar">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-2 my-auto d-none d-sm-none d-md-block d-lg-block">
                        <a href="/" style=" text-decoration: none;">
                            <h5 class="brand-name">Funda Ecom</h5>
                        </a>
                    </div>
                    <div class="col-md-5 my-auto">
                        <form role="search">
                            <div class="input-group">
                                <input type="search" placeholder="Search your product" class="form-control" />
                                <button class="btn bg-white" type="submit">
                                    <i class="fa fa-search"></i>
                                </button>
                            </div>
                        </form>
                    </div>
                    <div class="col-md-5 my-auto">
                        <ul class="nav justify-content-end">

                            <li class="nav-item">
                                <a class="nav-link" href="/cart">
                                    <i class="fa fa-shopping-cart"></i> Giỏ hàng
                                </a>
                            </li>
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                                    data-bs-toggle="dropdown" aria-expanded="false">
                                    <i class="fa fa-user"></i> {{ username() }}
                                </a>
                                <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                    <li><a class="dropdown-item" href="#"><i class="fa fa-user"></i> Profile</a></li>
                                    <li><a class="dropdown-item" href="/order"><i class="fa fa-list"></i> Đơn hàng</a>
                                    </li>

                                    <li><a class="dropdown-item" href="/add-product"><i class="fa fa-plus"></i> Thêm sản
                                            phẩm</a>
                                    </li>
                                    <li><a class="dropdown-item" href="/cart"><i class="fa fa-shopping-cart"></i> Giỏ
                                            hàng</a></li>
                                    <li><a class="dropdown-item" @click="logOut" href="#"><i class="fa fa-sign-out"></i>
                                            Đăng xuất</a></li>
                                </ul>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>

    </div>
</template>
<script>
export default {
    name: 'Navbar',
    methods: {
        logOut() {
            this.$store.dispatch('auth/logout');
            this.$router.push('/login')
        },
        username() {
            return this.$store.state.auth.user.firstname + " " + this.$store.state.auth.user.lastname;
        }
    }

}
</script>
<style scoped>
@import url(./Navbar.css);
@import url(https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css);
@import url(https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css);
</style>