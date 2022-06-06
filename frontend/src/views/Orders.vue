<template>
  <div class="bg-light">
    <nav-component></nav-component>
    <div class="mt-5">
      <div class="bg-image">
        <div class="banner mask">
          <div class="col-lg-6 text-light mx-auto h1 text-center text-white">
            Нарачки
          </div>
        </div>
      </div>
    </div>
    <div class="container p-5 text-center" v-show="!loggedIn">
      <span class="h1 text-danger">
        Не сте автентицирани за да имате пристап до оваа страна
      </span>
    </div>
    <div v-show="loggedIn" class="container-fluid p-5">
      <div class="row bg-white p-5 rounded">
        <div
          v-for="o in orders"
          :key="o.index"
          class="col-lg-5 bg-light rounded border-dark p-3 mx-auto my-5 border border-dark"
        >
          <div class="row">
            <div class="col-lg-12 my-3">
              Id: <span class="text-primary">{{ o.id }}</span>
            </div>
            <div class="col-lg-12 my-3">
              Дата на нарачка:
              <span class="text-primary">{{ o.dateOrder }}</span>
            </div>
            <div class="col-lg-12 my-3">
              Email адреса:<span class="text-primary"> {{ o.member.email
              }}</span>
            </div>
            <div class="col-lg-12 my-3">
              Име и презиме: <span class="text-primary">{{ o.member.firstName
              }} {{o.member.lastName}}</span>
            </div>
            <div class="col-lg-12 my-3">
              Име на компанија (Опционално):
              <span class="text-primary">{{ o.company }}</span>
            </div>
            <div class="col-lg-12 my-3">
              Телефон: <span class="text-primary">{{ o.phoneNumber }}</span>
            </div>
            <div class="col-lg-12 my-3">
              Адреса:
              <span class="text-primary"
                >{{ o.address }}, {{ o.city }}, {{ o.postal }}</span
              >
            </div>
            <div v-show="user == null ? false: user.role ===
          'ROLE_USER'" class="col-lg-12 my-3">
              Број за пратење:
              <span class="text-primary"
              >{{ o.trackingNumber }}</span
              >
            </div>
            <div v-show="user == null ? false: user.role ===
          'ROLE_ADMIN'" class="col-lg-12 my-3">
              Email порака со број за пратење:
              <div class="input-group" >
                <input
                  v-model="o.trackingNumber"
                  type="text"
                  class="form-control"
                  placeholder="Број за пратење"
                  aria-label="Број за пратење"
                  aria-describedby="track"
                />
                <div class="input-group-append">
                  <a
                    @click="updateTracking(o)"
                    class="btn btn-primary"
                    id="track"
                  >
                    Прати
                  </a>
                </div>
              </div>
            </div>
            <div class="col-lg-12 my-3">
              Производи:
              <div v-for="p in o.products" :key="p.index">
                <span class="text-primary">
                  {{ p.name }}, {{ p.type.name }}</span
                >
              </div>
            </div>
            <div class="col-lg-12 my-3">
              Достава до врата:
              <span class="text-primary">{{ o.toDoor ? "Да" : "Не" }}</span>
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
import OrderService from "@/repositories/ordersRepository";

export default {
  data() {
    return {
      orders: [],
    };
  },
  methods: {
    updateTracking(order) {
      OrderService.addTracking(order.id, order.trackingNumber)
    },
    loadOrders() {
      if(this.user.role !== "ROLE_ADMIN") {
        OrderService.allOrders(this.user.username).then((response) => {
          this.orders = response.data;
        })
      }
      else
        OrderService.allOrders().then((response) => {
          this.orders = response.data;
        })
    }
  },
  mounted() {
    this.loadOrders();
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

.order:hover {
  background: #f0f0f0;
}
</style>