<template>
  <div class="bg-light">
    <nav-component></nav-component>
    <div class="container mt-5">
      <div class="row bg-white my-5 p-5 rounded">
        <div class="col-lg-9 col-sm-12">
          <img class="img-fluid rounded" :src="product.imageUrl" />
        </div>
        <div class="col-lg-3 my-auto text-center">
          <div class="row">
            <div class="col-lg-12">
              <h3 class="font-weight-bold text-secondary">
                {{ product.name }}
              </h3>
            </div>
            <div class="col-lg-12">
              <div v-show="product.sale == 0">
                <h4 class="mt-5 font-weight-bold">{{ product.price }} ден.</h4>
              </div>
              <div v-show="product.sale != 0">
                <h4
                  style="text-decoration: line-through"
                  class="mt-5 text-primary"
                >
                  {{ product.price }}
                </h4>
                <h4 class="font-weight-bold">
                  {{ Math.round(product.price * (1 - product.sale / 100)) }}
                  ден.
                </h4>
              </div>
            </div>
            <div class="col-lg-12">
              <h4 class="mt-5">{{ product.type.name }}</h4>
            </div>
            <div class="col-lg-12 mb-5">
              <button
                :class="{ disabled: product.quantity <= 0 }"
                class="mt-5 btn btn btn-primary rounded"
                @click="addToCart(product)"
              >
                Додади во <i class="fas fa-shopping-bag"></i>
              </button>
              <h4
                class="text-danger font-weight-bold"
                v-show="product.quantity <= 0"
              >
                Распродадено
              </h4>
            </div>
          </div>
        </div>
        <div class="col-lg-12 border-top border-dark">
          <h4 class="mt-3">Продукти од истата колекција</h4>
          <div class="row">
            <div
              v-for="p in similar"
              :key="p.index"
              class="col-lg-3 text-center"
            >
              <a :href="getLink(p.id)"
                ><img class="img-fluid rounded" :src="p.imageUrl"
              /></a>
              <h5 class="mt-1 text-secondary font-weight-bold">
                {{ p.name }}
              </h5>
              <div v-show="p.quantity > 0" class="col-lg-12 my-1 mx-auto">
                <button
                  @click="addToCart(p)"
                  class="btn btn-primary rounded"
                  :class="{ disabled: p.quantity <= 0 }"
                >
                  Додади во <i class="fas fa-shopping-bag"></i>
                </button>
              </div>
              <div v-show="p.quantity <= 0" class="col-lg-12 my-1 mx-auto">
                <span class="btn btn-primary rounded disabled"
                  >Распродадено</span
                >
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <footer-component></footer-component>
  </div>
</template>

<script>
import Nav from "../components/Navbar.vue";
import Footer from "../components/Footer.vue";
import ProductService from "@/repositories/productsRepository";
export default {
  props: {
    id: {
      default: "",
    },
  },
  data() {
    return {
      product:{},
      similar: [],
    };
  },
  methods: {
    addToCart(item) {
      if(this.loggedIn)
        this.$store.commit("addToCart", item);
      else
        this.$router.push("/login")
    },
    getLink(id) {
      return "/details/" + id;
    },
    loadProducts() {
      ProductService.similar(this.id).then(response => {
        this.similar = response.data;
      })
    },
    loadProduct() {
      ProductService.details(this.id).then(response => {
        this.product = response.data;
      })
    }
  },
  created() {
    this.loadProduct();
    this.loadProducts();
  },
  computed: {
    loggedIn() {
      return this.$store.state.loggedIn;
    },
  },
  components: {
    "nav-component": Nav,
    "footer-component": Footer,
  },
};
</script>
<style scoped>
.image {
  max-width: 100px;
}
</style>
