@file:JvmName("ProviderRepository")

package com.br.apimercadolivre.searchproducts.repositories


fun provideMercadoLivreRepository(site: MeliSite) = ProdutoMercadoLivreRepository(site)