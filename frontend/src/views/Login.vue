<template>
  <div class="bg-light">
    <main class="form-signin">
      <form class="mx-auto">
        <router-link to="/">
          <h1 class="text-center">e-Shop</h1>
        </router-link>
        <h1 class="h3 fw-normal mt-3">Ве молиме најавете се</h1>
        <label for="inputusername" class="visually-hidden mt-3"
          >Корисничко име</label
        >
        <input
          v-model="username"
          type="username"
          id="inputusername"
          class="form-control"
          required
          autofocus
        />
        <label for="inputPassword" class="visually-hidden mt-3">Пасворд</label>
        <input
          v-model="password"
          type="password"
          id="inputPassword"
          class="form-control"
          required
        />
        <div v-show="errorMessage != undefined" class="text-danger">
          {{ errorMessage }}
        </div>
        <button
          class="w-100 btn btn-lg btn-primary mt-3"
          type="submit"
          @click.prevent="loginUser()"
        >
          Најави се
        </button>
        <div class="text-center">
        Немате акаунт?
        <router-link to="/register">
          Регистрирај се</router-link>
        </div>
      </form>
    </main>
  </div>
</template>
<script>
import MemberService from "@/repositories/memberRepository";

export default {
  data() {
    return {
      username: undefined,
      password: undefined,
      errorMessage: undefined,
      user: {},
    };
  },
  methods: {
    loginUser() {
      MemberService.login(this.username, this.password).then((response) => {
        this.user = response.data;
        this.$store.commit("doLogIn", this.user);
        this.$router.push("/")
      }).catch(() => this.errorMessage= "Настана грешка, обидете се повторно!")
    },
  },
};
</script>

<style scoped>
.form-signin {
  height: 100vh;
  display: flex;
  align-items: center;
}
</style>