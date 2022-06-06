<template>
  <div class="bg-light">
    <nav-component></nav-component>
    <div class="mt-5">
      <div class="bg-image">
        <div class="banner mask">
          <div class="col-lg-6 text-light mx-auto h1 text-center text-white">
            Кошничка
          </div>
        </div>
      </div>
    </div>
    <div class="container p-5">
      <div class="row bg-white p-5 rounded">
        <div
          class="bg-light col-lg-12 border border-dark border-bottom-0 rounded-top py-3"
        >
          <div class="row">
            <div class="col-lg-2"></div>
            <div class="col-lg-5">Производ</div>
            <div class="col-lg-5">Цена</div>
          </div>
        </div>
        <div
          v-for="p in products"
          :key="p.id"
          class="col-lg-12 bg-white border border-dark border-bottom-0 py-3"
        >
          <div class="row">
            <div class="col-lg-2 my-auto text-center">
              <button
                @click="deleteFromCart(p)"
                class="btn btn-sm btn-danger rounded"
              >
                Отстрани
              </button>
            </div>
            <div class="col-lg-1 my-auto">
              <img class="img-fluid" :src="p.imageUrl" :alt="p.name" />
            </div>
            <div class="col-lg-4 text-primary my-auto">{{ p.name }}</div>
            <div class="col-lg-5 text-dark my-auto">
              {{
                p.sale == 0 ? p.price : Math.round(p.price * (1 - p.sale / 100))
              }}
              ден.
            </div>
          </div>
        </div>
        <div class="bg-light col-lg-12 border border-dark rounded-bottom py-3">
          <div class="row">
            <div class="col-lg-6"></div>
            <div class="col-lg-1">Вкупно:</div>
            <div class="col-lg-2 font-weight-bold">{{ total }} ден.</div>
            <div class="col-lg-3">
              <router-link
                to="/payment"
                :class="{ disabled: total == 0 }"
                class="btn btn-sm btn-primary rounded"
              >
                Продолжи кон плаќање
              </router-link>
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
export default {
  data() {
    return {};
  },
  components: {
    "nav-component": Nav,
    "footer-component": Footer,
  },
  methods: {
    deleteFromCart(item) {
      this.$store.commit("removeFromCart", item);
    },
  },
  computed: {
    total() {
      let sum = 0;
      for (let p of this.products) {
        sum +=
          p.sale == 0
            ? parseInt(p.price)
            : Math.round(p.price * (1 - p.sale / 100));
      }
      return sum;
    },
    products() {
      return this.$store.state.cart;
    },
  },
};
</script>

<style scoped>
.bg-image {
  background: url("../assets/images/other-background.png") fixed center
    no-repeat;
  background-size: cover;
  -o-background-size: cover;
  -moz-background-size: cover;
  -webkit-background-size: cover;
}

.mask {
  background-color: rgba(0, 0, 0, 0.6);
}

.banner {
  height: 33vh;
  display: flex;
  align-items: center;
}
</style>