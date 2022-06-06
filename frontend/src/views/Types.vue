<template>
  <div class="bg-light">
    <nav-component></nav-component>
    <div class="container vh mt-5">
      <div class="row py-5">
        <div class="col-lg-12 text-center">
          <span
            v-show="selectedType.name == undefined"
            class="h1 font-weight-bold text-primary"
            >Одберете Категорија</span
          >
          <ul
            class="nav nav-pills justify-content-center"
            :class="{ 'mt-5': selectedType.name == undefined }"
          >
            <li
              v-for="type in types"
              :key="type.id"
              class="h5 font-weight-bold text-uppercase nav-item mx-1 rounded"
            >
              <span
                class="nav-link"
                :class="{ active: selectedType.name == type.name }"
                @click="changeSelectedType(type)"
                >{{ type.name }}</span
              >
            </li>
            <li
              class="h5 font-weight-bold text-uppercase nav-item mx-1"
              v-show="user == null ? false: user.role ===
          'ROLE_ADMIN'"
            >
              <span
                ><input
                  @keyup.enter.prevent="addNewType()"
                  v-model="newType"
                  type="text"
                  class="nav-link"
                  placeholder="Додади нова колекција"
              /></span>
            </li>
          </ul>
        </div>
        <div
          v-for="product in products"
          v-show="product.type.name == selectedType.name"
          :key="product.id"
          class="col-lg-4"
        >
          <div
            class="col-lg-10 mx-auto my-5 bg-white rounded border border-dark text-center p-3"
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
import ProductService from "@/repositories/productsRepository";
import TypeService from "@/repositories/typesRepository";
export default {
  data() {
    return {
      newType: undefined,
      types: [],
      selectedType: {},
      products: [],
    };
  },
  components: {
    "nav-component": Nav,
    "footer-component": Footer,
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
    changeSelectedType(type) {
      this.selectedType = type;
    },
    addNewType() {
      TypeService.add({name: this.newType}).then( () => {
        this.loadTypes()
        this.newType = "";
      });

    },
    loadProducts() {
      ProductService.allProducts().then(response => {
        this.products = response.data;
        console.log(this.products)
      })
    },
    loadTypes() {
      TypeService.allTypes().then(response => {
        this.types = response.data;
        this.selectedType = this.types[0];
      })
    }
  },
  created() {
    this.loadProducts();
    this.loadTypes();
  },
  computed: {
    loggedIn() {
      return this.$store.state.loggedIn;
    },
    user() {
      return this.$store.state.user;
    }
  },
};
</script>

<style scoped>
.nav-item {
  cursor: pointer;
}
.vh {
  min-height: 80vh;
}
</style>
