<template>
  <div class="bg-light">
    <main class="form-signin">
      <form class="mx-auto">
        <router-link to="/">
          <h1 class="text-center">e-Shop</h1>
        </router-link>
        <h1 class="h3 fw-normal mt-3">Ве молиме верифицирајте се</h1>
        <label for="inputusername" class="visually-hidden mt-3"
        >Внесете го кодот кој ви го пративме по мејл</label
        >
        <input
            v-model="code"
            type="text"
            id="inputusername"
            class="form-control"
            required
            autofocus
        />
        <div v-show="errorMessage != undefined" class="text-danger">
          {{ errorMessage }}
        </div>
        <div v-show="successMessage != undefined" class="text-success">
          {{ successMessage }}
        </div>
        <button
            class="w-100 btn btn-lg btn-primary mt-3"
            type="submit"
            @click.prevent="verify()"
        >
          Верифицирај се
        </button>
      </form>
    </main>
  </div>
</template>

<script>
import MemberService from "@/repositories/memberRepository";
export default {
  data() {
    return {
      code: undefined,
      successMessage: undefined,
      errorMessage: undefined,
    };
  },
  methods: {
    verify() {
      MemberService.verify(this.code, this.user.username).then((response) => {
        this.$store.commit("doLogIn", response.data);
        this.successMessage = "Успешна верификација!";
        let vm = this;
        setTimeout(function (){
          vm.$router.push("/")
        }, 1000);
      }).catch(() => {
        this.errorMessage = "Неуспешна верификација, обидете се повторно!";
      })
    },
  },
  computed: {
    user() {
      return this.$store.state.user;
    }
  }
};
</script>

<style scoped>
.form-signin {
  height: 100vh;
  display: flex;
  align-items: center;
}
</style>