<template>
  <div class="bg-light">
    <nav-component></nav-component>
    <div class="mt-5">
      <div class="bg-image">
        <div class="banner mask">
          <div class="col-lg-6 text-light mx-auto h1 text-center text-white">
            Нарачка
          </div>
        </div>
      </div>
    </div>
    <div class="container p-5">
      <form class="row bg-white p-5 rounded">
        <div class="col-lg-6 py-5 text-dark text-break mx-auto">
          <span class="h5 font-weight-bold text-uppercase text-primary"
            >ДОСТАВА</span
          >
          <br />
          Имаме достава низ цела Македонија, времето на достава е 2 до 5 работни
          дена. Можност за достава имаме до врата и со подигнување. Цена за
          достава: 150 денари
        </div>
        <div class="form-group col-lg-12 mx-auto">
          <label for="email"
            >Email адреса <span class="text-danger">*</span></label
          >
          <input
            v-model="user.email"
            type="email"
            class="form-control bg-light"
            id="email"
          />
        </div>
        <div class="form-group col-lg-12 mx-auto">
          <div class="row">
            <div class="col-lg-6">
              <label for="firstName"
                >Име <span class="text-danger">*</span></label
              >
              <input
                v-model="user.firstName"
                type="text"
                class="form-control bg-light"
                id="firstName"
                required
              />
            </div>
            <div class="col-lg-6">
              <label for="lastName"
                >Презиме <span class="text-danger">*</span></label
              >
              <input
                v-model="user.lastName"
                type="text"
                class="form-control bg-light"
                id="lastName"
                required
              />
            </div>
          </div>
        </div>
        <div class="form-group col-lg-12 mx-auto">
          <label for="phone">Телефон <span class="text-danger">*</span></label>
          <input
            v-model="phone"
            type="text"
            class="form-control bg-light"
            id="phone"
            required
          />
        </div>
        <div class="form-group col-lg-12 mx-auto">
          <label for="company"
            >Име на компанија
            <span class="text-danger text-uppercase">(опционално)</span></label
          >
          <input
            v-model="company"
            type="text"
            class="form-control bg-light"
            id="company"
          />
        </div>
        <div class="form-group col-lg-12 mx-auto">
          <div class="row">
            <div class="col-lg-4">
              <label for="adress"
                >Адреса <span class="text-danger">*</span></label
              >
              <input
                v-model="address"
                type="text"
                class="form-control bg-light"
                id="adress"
                required
              />
            </div>
            <div class="col-lg-4">
              <label for="city">Град <span class="text-danger">*</span></label>
              <input
                v-model="city"
                type="text"
                class="form-control bg-light"
                id="city"
                required
              />
            </div>
            <div class="col-lg-4">
              <label for="postal"
                >Поштенски код <span class="text-danger">*</span></label
              >
              <input
                v-model="postal"
                type="text"
                class="form-control bg-light"
                id="postal"
                required
              />
            </div>
          </div>
        </div>
        <div class="form-group col-lg-12 mx-auto">
          <label for="phone"
            >Метод на испорака <span class="text-danger">*</span></label
          >
          <ul class="nav nav-pills justify-content-center">
            <li class="h5 font-weight-bold nav-item mx-1">
              <span
                class="nav-link border-bottom border-dark"
                :class="{ active: baseOrderType }"
                @click="changeOrderType('base')"
                >БЕСПЛАТНО Kарго до БАЗА во секој град</span
              >
            </li>
            <li class="h5 font-weight-bold nav-item mx-1">
              <span
                class="nav-link border-bottom border-dark"
                :class="{ active: doorOrderType }"
                @click="changeOrderType('door')"
                >Достава до ВРАТА: 50 ден.</span
              >
            </li>
          </ul>
        </div>
        <div class="col-lg-12 mx-auto mt-5">
          <span class="h4 mr-3 my-auto"
            >Вкупно: <u>{{ total }}</u> ден.</span
          >
          <button
            @click.prevent="createOrder()"
            class="mx-auto btn btn-primary"
          >
            Потврди
          </button>
        </div>
      </form>
    </div>
    <footer-component></footer-component>
  </div>
</template>

<script>
import Nav from "../components/Navbar.vue";
import Footer from "../components/Footer.vue";
import OrderService from "@/repositories/ordersRepository";
export default {
  data() {
    return {
      //mozi ke pritreba
      delivery: 150,
      baseOrderType: true,
      doorOrderType: false,
      company: undefined,
      phone: undefined,
      address: undefined,
      city: undefined,
      postal: undefined,
      orders: [],
    };
  },
  components: {
    "nav-component": Nav,
    "footer-component": Footer,
  },
  methods: {
    changeOrderType(text) {
      if (this.doorOrderType) {
        this.delivery -= 50;
      }
      this.baseOrderType = false;
      this.doorOrderType = false;
      if (text === "door") {
        this.doorOrderType = true;
        this.delivery += 50;
      } else {
        this.baseOrderType = true;
      }
    },
    createOrder() {
      return OrderService.add({
        username: this.user.username,
        phoneNumber: this.phone,
        address: this.address,
        city: this.city,
        postalCode: this.postal,
        toDoor: this.doorOrderType,
        trackingNumber: null,
        productIds: this.products.map(p => p.id),
        company: this.company === undefined ? null : this.company
      }).then(() => {
        alert("Успешно направена нарачка!");
        this.$store.commit("setCartToEmpty");
        this.$router.push("/");
      })
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
      return sum + this.delivery;
    },
    products() {
      return this.$store.state.cart;
    },
    user() {
      return this.$store.state.user;
    }
  },
};
</script>
<style scoped>
.nav-link {
  cursor: pointer;
}
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
