<template>
  <div class="bg-light">
    <nav-component></nav-component>
    <div>
      <div class="bg-image mt-5">
        <div class="banner mask">
          <div class="mx-auto h1 text-white text-center">Вашиот интернет <u
              class="text-secondary ">e
            Шоп</u></div>
        </div>
      </div>
    </div>
    <div class="container">
      <div class="row py-5">
        <div class="col-lg-12 text-center">
          <span class="h1 font-weight-bold text-uppercase"
            >Нашите Најбарани производи</span
          >
        </div>
        <div v-for="product in best" :key="product.index" class="col-lg-4">
          <div
            class="col-lg-12 mx-auto my-5 bg-white rounded border border-dark text-center p-3"
          >
            <img
              class="img-fluid rounded"
              :src="product.imageUrl"
              :alt="product.name"
            />
            <h4 class="text-secondary font-weight-bold">{{ product.name }}</h4>
            <div class="row mx-auto">
              <div class="col-lg-12 my-1 mx-auto">
                <a
                  :href="getLink(product.id)"
                  class="btn btn-outline-primary rounded"
                  >Повеќе</a
                >
              </div>
              <div v-show="product.quantity > 0" class="col-lg-12 my-1 mx-auto">
                <button
                  @click="addToCart(product)"
                  class="btn btn-primary rounded"
                  :class="{ disabled: product.quantity <= 0 }"
                >
                  Додади во <i class="fas fa-shopping-bag"></i>
                </button>
              </div>
              <div
                v-show="product.quantity <= 0"
                class="col-lg-12 my-1 mx-auto"
              >
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
import ProductService from "../repositories/productsRepository";
export default {
  data() {
    return {
      best: []
    };
  },
  methods: {
    getLink(id) {
      return "/details/" + id;
    },
    addToCart(item) {
      if(this.loggedIn)
        this.$store.commit("addToCart", item);
      else
        this.$router.push("/login")
    },
  },
  created(){
    ProductService.bestProducts().then(response => {
      this.best = response.data;
    })
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
.bg-image {
  background: url("https://cdn.corporatefinanceinstitute.com/assets/product-mix3.jpeg") fixed center
  no-repeat;
  background-size: cover;
  -o-background-size: cover;
  -moz-background-size: cover;
  -webkit-background-size: cover;
}

.mask {
  background-color: rgba(52, 152, 219, 0.6);
}

.banner {
  height: 80vh;
  display: flex;
  align-items: center;
}
</style>
