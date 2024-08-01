<template>
    <div class="row">
        <div v-for="(item, index) in listProduct" :key="item" class="col-lg-3 col-md-6 col-sm-6">
            <a :href="'/product/' + item.id" class="card w-100 my-2 shadow-2-strong" id="myCard">
                <el-image style="width: 304; height: 304px" :src="item.image" fit="cover" />
                <div class="card-body d-flex flex-column">
                    <div class="d-flex flex-row">
                        <h5 class="mb-1 me-1">{{ formatCurrency(item.price) }}</h5>
                        <span class="text-danger"><s>{{ formatCurrency(item.price + 10000) }}</s></span>
                    </div>
                    <p class="card-text">{{ item.name }}</p>
                </div>
            </a>
        </div>
    </div>
</template>
<script>
export default {
    name: 'ListProduct',
    props: {
        listProduct: {
            type: Array,
        }
    },
    methods: {
        formatCurrency(amount) {
            return amount.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
        }
    }
}
</script>

<style scoped>
@import url(./ListProduct.css);
</style>