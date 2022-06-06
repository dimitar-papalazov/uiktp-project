<template>
  <div>
    <nav class="navbar navbar-expand-lg navbar-light bg-light fixed-top">
      <div class="container">
        <router-link class="navbar-brand text-primary font-weight-bold" to="/"
          >
          e-Shop
        </router-link>
        <button
          class="navbar-toggler"
          type="button"
          data-toggle="collapse"
          data-target="#navbarSupportedContent"
          aria-controls="navbarSupportedContent"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav ml-auto">
            <li class="nav-item ml-auto mr-2 mr-lg-3">
              <router-link class="nav-link" to="/products"
                >Производи</router-link
              >
            </li>
            <li class="nav-item ml-auto mr-2 mr-lg-3">
              <router-link class="nav-link" to="/types"
                >Категории</router-link
              >
            </li>
            <li  class="nav-item ml-auto mr-2 mr-lg-3">
              <router-link class="nav-link" to="/contact">Контакт</router-link>
            </li>
            <li v-show="loggedIn" class="nav-item ml-auto mr-2 mr-lg-3">
              <router-link class="nav-link" to="/orders">Нарачки</router-link>
            </li>
            <li
                v-show="!loggedIn"
                class="nav-item ml-auto mr-2 mr-lg-3"

            >
              <router-link class="nav-link" to="/login">Најави се</router-link>
            </li>
            <li
                v-show="!loggedIn"
                class="nav-item ml-auto mr-2 mr-lg-3"

            >
              <router-link class="nav-link" to="/register">Регистрирај
                се</router-link>
            </li>
            <li
                v-show="loggedIn"
              class="nav-item ml-auto mr-2 mr-lg-3"
              @click="logOut()"
              style="cursor: pointer"
            >
              <span class="nav-link">Одјави се</span>
            </li>
            <li  class="nav-item ml-auto mr-2">
              <router-link class="nav-link text-primary" to="/shoppingCart"
                ><i class="fas fa-shopping-bag fa-lg"></i
                ><span class="badge badge-pill badge-primary mx-1">{{
                  cartLength
                }}</span></router-link
              >
            </li>
          </ul>
        </div>
      </div>
    </nav>
  </div>
</template>

<script>
export default {
  methods: {
    logOut() {
      this.$store.commit("doLogOff");
      this.$router.push("/");
    },
  },
  computed: {
    loggedIn() {
      return this.$store.state.loggedIn;
    },
    cartLength() {
      return this.$store.state.cart.length;
    },
  },
};
</script>

<style scoped>
.fixed-top {
  width: 100vw;
}
</style>
