<template>
  <div class="border-top border-dark">
    <footer class="bg-light text-lg-start">
      <div class="container p-4">
        <div class="row">
          <div class="col-lg-4 col-md-12 my-lg-auto px-5">
            <h1 class="text-primary">e-Shop</h1>
          </div>

          <div class="col-lg-4 col-md-12 my-4 my-md-0 px-5">
            <h5 class="text-uppercase mx-auto">Достава</h5>

            <p>
              Имаме достава низ цела Македонија, времето на достава е 2 до 5
              работни дена. Можност за достава имаме до врата и со подигнување.
            </p>
            <p>
              Достава низ цела Македонија<strong class="h5 font-weight-bold">
              150 ден.</strong>
            </p>
          </div>

          <div class="col-lg-4 col-md-12 my-4 my-md-0 px-5">
            <h5 class="text-uppercase mx-auto">Линкови</h5>

            <ul class="list-unstyled">
              <li>
                <router-link to="/products" class="text-dark"
                  >Производи</router-link
                >
              </li>
              <li>
                <router-link to="/types" class="text-dark"
                  >Категории</router-link
                >
              </li>
              <li>
                <router-link to="/contact" class="text-dark"
                  >Контакт</router-link
                >
              </li>
            </ul>
          </div>
        </div>
      </div>
      <div class="container-fluid">
        <div class="row bg-dark text-white">
          <span class="ml-5 my-auto py-3">© 2022 Copyright: e-Shop</span>
        </div>
      </div>
    </footer>
  </div>
</template>