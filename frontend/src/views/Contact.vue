<template>
  <div class="bg-light">
    <nav-component></nav-component>
    <div class="mt-5">
      <div class="bg-image">
        <div class="banner mask">
          <div class="col-lg-6 text-light mx-auto h1 text-center text-white">
            Контакт
          </div>
        </div>
      </div>
    </div>
    <div class="container p-5">
      <form class="row bg-white p-5 rounded">
        <div
          class="col-lg-12 py-5 text-center text-primary h5 font-weight-bold text-uppercase text-break mx-auto"
        >
          Контактирајте не
        </div>
        <div class="form-group col-lg-12 mx-auto">
          <div class="row">
            <div class="col-lg-6">
              <label for="firstName"
                >Име <span class="text-danger">*</span></label
              >
              <input
                v-model="firstName"
                required
                type="text"
                class="form-control bg-light"
                id="firstName"
              />
            </div>
            <div class="col-lg-6">
              <label for="lastName"
                >Презиме <span class="text-danger">*</span></label
              >
              <input
                v-model="lastName"
                required
                type="text"
                class="form-control bg-light"
                id="lastName"
              />
            </div>
          </div>
        </div>
        <div class="form-group col-lg-12 mx-auto">
          <label for="textarea"
            >Порака <span class="text-danger">*</span></label
          >
          <textarea
            v-model="message"
            required
            class="form-control bg-light"
            id="textarea"
            rows="3"
          ></textarea>
        </div>
        <div class="col-lg-12 mx-auto">
          <a
            :href="
              'mailto:toocool.business@gmail.com?&subject=Порака од ' +
              firstName +
              ' ' +
              lastName +
              '&body=' +
              message
            "
            class="mx-auto btn btn-primary"
            >Испрати</a
          >
        </div>
      </form>
    </div>
    <footer-component></footer-component>
  </div>
</template>

<script>
import Nav from "../components/Navbar.vue";
import Footer from "../components/Footer.vue";
export default {
  data() {
    return {
      firstName: undefined,
      lastName: undefined,
      message: undefined,
    };
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
</style>
