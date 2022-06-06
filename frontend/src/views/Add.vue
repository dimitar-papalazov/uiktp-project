<template>
  <div class="bg-light">
    <nav-component></nav-component>
    <div class="container p-5 mt-5" >
      <div class="row bg-white p-5 rounded">
        <div class="col-lg-6">
          <img class="img-fluid" :src="product.imageUrl" />
          <label>Внесете url за слика на производот</label>
          <input
            type="text"
            class="form-control my-auto"
            placeholder="Внесете url за слика на производот"
            v-model="product.imageUrl"
          />
        </div>
        <div class="col-lg-6">
          <div class="row">
            <div class="col-lg-12">
              <label>Внесете име на производот</label>
              <input
                placeholder="Внесете име на производот"
                type="text"
                v-model="product.name"
                class="form-control my-auto"
              />
            </div>
            <div class="col-lg-12 mt-5">
              <label>Внесете цена на производот</label>
              <input
                placeholder="Внесете цена на производот"
                type="text"
                v-model="product.price"
                class="form-control my-auto"
              />
            </div>
            <div class="col-lg-12 mt-5">
              <label for="type">Одберете категорија на производ</label>
              <select
                id="type"
                v-model="product.type"
                class="custom-select form-control"
              >
                <option v-for="type in types" :key="type.id" :value="type.id">
                  {{ type.name }}
                </option>
              </select>
            </div>
            <div class="col-lg-12 mt-5">
              <label>Внесете залиха на производот</label>
              <input
                placeholder="Внесете залиха на производот"
                type="text"
                v-model="product.quantity"
                class="form-control my-auto"
              />
            </div>
            <div class="col-lg-12 mt-5">
              <label for="type">Внесете попуст на производот</label>
              <input
                placeholder="Внесете попуст на производот"
                type="text"
                v-model="product.sale"
                class="form-control my-auto"
              />
            </div>
            <div class="col-lg-12">
              <button
                @click="addToDb()"
                class="mt-5 btn btn-lg btn-primary rounded"
              >
                Зачувај
              </button>
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
      types: [],
      product: {
        price: undefined,
        name: undefined,
        type: undefined,
        quantity: undefined,
        imageUrl: undefined,
        sale: null,
        sold: 0,
      },
    };
  },
  methods: {
    addToDb() {
      console.log(this.product)
      ProductService.add(this.product)
          .then(() => {
            alert("Успешно додаден производ!")
            this.$router.push("/");
          })
          .catch((e) => {
        console.log(e)
      })
    },
  },
  created(){
    ProductService.allProducts().then(response=>{console.log(response.data[0])})
    TypeService.allTypes().then(response => {
      this.types = response.data;
      console.log(this.types[0])
    }).catch(e => { console.log(e) })
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
