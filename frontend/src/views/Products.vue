<template>
  <div class="bg-light">
    <nav-component></nav-component>
    <div class="container mt-5">
      <div class="row py-5">
        <div class="col-lg-12 text-center">
          <span class="h2 font-weight-bold text-uppercase">Сите Производи</span>
        </div>
        <div class="col-lg-12 my-3">
          <router-link to="/add" v-show="user == null ? false: user.role ===
          'ROLE_ADMIN'" class="btn btn-secondary">
            Додади нов производ
          </router-link>
        </div>
        <div v-for="product in products" :key="product.index" class="col-lg-4">
          <div
            class="col-lg-12 mx-auto my-5 bg-white rounded border border-dark text-center p-3"
          >
            <span v-show="user == null ? false: user.role ===
          'ROLE_ADMIN'"
              style="cursor: pointer"
              class="text-danger font-weight-bold"
              title="Delete"
              @click="deleteItem(product)"
              >X</span
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
              <div class="col-lg-12 my-1 mx-auto">
                <a
                  :href="getEditLink(product.id)"
                  v-show="user == null ? false: user.role ===
          'ROLE_ADMIN'"
                  class="btn btn-outline-primary rounded"
                >
                  Промени
                </a>
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
import ProductService from "@/repositories/productsRepository";
export default {
  data() {
    return {
      products: [],
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
    getEditLink(id) {
      return "/edit/" + id;
    },
    deleteItem(product) {
      ProductService.delete(product.id).then(() => {
        ProductService.allProducts().then(response => {
          this.products = response.data;
        })
      }).catch((e) => {
        console.log(e);
        ProductService.allProducts().then(response => {
          this.products = response.data;
        })
      })
    },
  },
  created(){
    ProductService.allProducts().then(response => {
      this.products = response.data;
    })
  },
  computed: {
    loggedIn() {
      return this.$store.state.loggedIn;
    },
    user() {
      return this.$store.state.user;
    }
  },
  components: {
    "nav-component": Nav,
    "footer-component": Footer,
  },
};
</script>
