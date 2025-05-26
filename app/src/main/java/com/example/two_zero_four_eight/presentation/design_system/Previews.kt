package com.example.two_zero_four_eight.presentation.design_system

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview

//PHONES START

@Preview(name = "360x672PortFull", widthDp = 360, heightDp = 672)
@Preview(name = "360x672LandFull", widthDp = 672, heightDp = 336)
@Preview(name = "360x672PortSimp", widthDp = 360, heightDp = 331)
@Preview(name = "360x672LandSimp", widthDp = 331, heightDp = 336)
annotation class DevicePreview360x672



//2.7  QVGA slider API 29 2.7 240x320
@Preview(name = "320x402PortFull", widthDp = 320, heightDp = 402)
@Preview(name = "320x402LandFull", widthDp = 426, heightDp = 296)
@Preview(name = "320x402PortSimp", widthDp = 320, heightDp = 196)
@Preview(name = "320x402LandSimp", widthDp = 220, heightDp = 296)
annotation class DevicePreview320x402

//3.4 WQVGA API 29 3.4 240x432
@Preview(name = "320x552PortFull", widthDp = 320, heightDp = 552)
@Preview(name = "320x552LandFull", widthDp = 576, heightDp = 296)
@Preview(name = "320x552PortSimp", widthDp = 320, heightDp = 270)
@Preview(name = "320x552LandSimp", widthDp = 282, heightDp = 296)
annotation class DevicePreview320x552

//5.1  WVGA API 29 5.1 480x800
@Preview(name = "480x776PortFull", widthDp = 480, heightDp = 776)
@Preview(name = "480x776LandFull", widthDp = 800, heightDp = 456)
@Preview(name = "480x776PortSimp", widthDp = 480, heightDp = 383)
@Preview(name = "480x776LandSimp", widthDp = 395, heightDp = 456)
annotation class DevicePreview480x776

//Nexus 4 API 29 4.7 768x1280
@Preview(name = "384x568PortFull", widthDp = 384, heightDp = 568)
@Preview(name = "384x568LandFull", widthDp = 592, heightDp = 360)
@Preview(name = "384x568PortSimp", widthDp = 384, heightDp = 279)
@Preview(name = "384x568LandSimp", widthDp = 291, heightDp = 360)
annotation class DevicePreview384x568

//Nexus 5 API 30 4.95 1080x1920
@Preview(name = "360x568PortFull", widthDp = 360, heightDp = 568)
@Preview(name = "360x568LandFull", widthDp = 592, heightDp = 336)
@Preview(name = "360x568PortSimp", widthDp = 360, heightDp = 279)
@Preview(name = "360x568LandSimp", widthDp = 291, heightDp = 336)
annotation class DevicePreview360x568

//Nexus 6 API 29 5.96 1440x2560
@Preview(name = "411x659PortFull", widthDp = 411, heightDp = 659)
@Preview(name = "411x659LandFull", widthDp = 683, heightDp = 387)
@Preview(name = "411x659PortSimp", widthDp = 411, heightDp = 324)
@Preview(name = "411x659LandSimp", widthDp = 336, heightDp = 387)
annotation class DevicePreview411x659

//Nexus 7 (2012) API 29 7.0 800x1200
@Preview(name = "600x889PortFull", widthDp = 600, heightDp = 889)
@Preview(name = "600x889LandFull", widthDp = 961, heightDp = 528)
@Preview(name = "600x889PortSimp", widthDp = 600, heightDp = 439)
@Preview(name = "600x889LandSimp", widthDp = 475, heightDp = 528)
annotation class DevicePreview600x889

//Nexus 9 API 29 8.86 2048x1536
@Preview(name = "1024x696PortFull", widthDp = 1024, heightDp = 696)
@Preview(name = "1024x696LandFull", widthDp = 768, heightDp = 952)
@Preview(name = "1024x696PortSimp", widthDp = 507, heightDp = 696)
@Preview(name = "1024x696LandSimp", widthDp = 768, heightDp = 471)
annotation class DevicePreview1024x696

//Nexus S API 29 4.0 480X800
@Preview(name = "320x509PortFull", widthDp = 320, heightDp = 509)
@Preview(name = "320x509LandFull", widthDp = 533, heightDp = 296)
@Preview(name = "320x509PortSimp", widthDp = 320, heightDp = 250)
@Preview(name = "320x509LandSimp", widthDp = 262, heightDp = 296)
annotation class DevicePreview320x509

//Pixel C API 29 9.94 2560x1800
@Preview(name = "900x1200PortFull", widthDp = 900, heightDp = 1200)
@Preview(name = "900x1200LandFull", widthDp = 1280, heightDp = 820)
@Preview(name = "900x1200PortSimp", widthDp = 900, heightDp = 595)
@Preview(name = "900x1200LandSimp", widthDp = 635, heightDp = 820)
annotation class DevicePreview900x1200

//PHONES END

//TABLET START
//Pixel Tablet API 30 10.95 2560X1600
@Preview(name = "800x1208PortFull", widthDp = 800, heightDp = 1208)
@Preview(name = "800x1208LandFull", widthDp = 1280, heightDp = 728)
@Preview(name = "800x1208PortSimp", widthDp = 800, heightDp = 599)
@Preview(name = "800x1208LandSimp", widthDp = 635, heightDp = 728)
annotation class DevicePreview800x1208


//Phones
//Medium Phone API 30 6.4 1080x2400
@Preview(name = "411x842PortFull", widthDp = 411, heightDp = 842)
@Preview(name = "411x842LandFull", widthDp = 866, heightDp = 387)
@Preview(name = "411x842PortSimp", widthDp = 411, heightDp = 416)
@Preview(name = "411x842LandSimp", widthDp = 428, heightDp = 387)
annotation class DevicePreview411x842

//Pixel 8 Pro API 30 6.7 1344x2992
@Preview(name = "448x904PortFull", widthDp = 448, heightDp = 904)
@Preview(name = "448x904LandFull", widthDp = 904, heightDp = 420)
@Preview(name = "448x904PortSimp", widthDp = 448, heightDp = 447)
@Preview(name = "448x904LandSimp", widthDp = 447, heightDp = 420)
annotation class DevicePreview448x904

//Pixel 8 API 30 6.17 1080x2400
@Preview(name = "411x814PortFull", widthDp = 411, heightDp = 814)
@Preview(name = "411x814LandFull", widthDp = 814, heightDp = 383)
@Preview(name = "411x814PortSimp", widthDp = 411, heightDp = 402)
@Preview(name = "411x814LandSimp", widthDp = 402, heightDp = 383)
annotation class DevicePreview411x814

//Pixel 7 Pro API 30 6.71 1440x3120
@Preview(name = "411x804PortFull", widthDp = 411, heightDp = 804)
@Preview(name = "411x804LandFull", widthDp = 804, heightDp = 383)
@Preview(name = "411x804PortSimp", widthDp = 411, heightDp = 397)
@Preview(name = "411x804LandSimp", widthDp = 397, heightDp = 383)
annotation class DevicePreview411x804

//Pixel 6 Pro API 29 6.7 1440x3120
@Preview(name = "411x819PortFull", widthDp = 411, heightDp = 819)
@Preview(name = "411x819LandFull", widthDp = 843, heightDp = 387)
@Preview(name = "411x819PortSimp", widthDp = 411, heightDp = 404)
@Preview(name = "411x819LandSimp", widthDp = 416, heightDp = 387)
annotation class DevicePreview411x819

//Pixel 5 API 29 6.0 1080x2340
@Preview(name = "392x778PortFull", widthDp = 392, heightDp = 778)
@Preview(name = "392x778LandFull", widthDp = 802, heightDp = 368)
@Preview(name = "392x778PortSimp", widthDp = 392, heightDp = 384)
@Preview(name = "392x778LandSimp", widthDp = 396, heightDp = 368)
annotation class DevicePreview392x778

//Pixel 4a API 33 5.8 1080x2340
@Preview(name = "393x777PortFull", widthDp = 393, heightDp = 777)
@Preview(name = "393x777LandFull", widthDp = 801, heightDp = 345)
@Preview(name = "393x777PortSimp", widthDp = 393, heightDp = 396)
@Preview(name = "393x777LandSimp", widthDp = 396, heightDp = 345)
annotation class DevicePreview393x777

//Pixel 4 XL API 29 6.3 1440x3040
@Preview(name = "411x792PortFull", widthDp = 411, heightDp = 792)
@Preview(name = "411x792LandFull", widthDp = 820, heightDp = 383)
@Preview(name = "411x792PortSimp", widthDp = 411, heightDp = 391)
@Preview(name = "411x792LandSimp", widthDp = 405, heightDp = 383)
annotation class DevicePreview411x792

//Pixel 4 API 30 5.7 1080x2280 2
@Preview(name = "392x718PortFull", widthDp = 392, heightDp = 718)
@Preview(name = "392x718LandFull", widthDp = 718, heightDp = 364)
@Preview(name = "392x718PortSimp", widthDp = 392, heightDp = 354)
@Preview(name = "392x718LandSimp", widthDp = 354, heightDp = 364)
annotation class DevicePreview392x718

//Pixel 3a XL API 29 6.0 1080x2160
@Preview(name = "432x792PortFull", widthDp = 432, heightDp = 792)
@Preview(name = "432x792LandFull", widthDp = 816, heightDp = 408)
@Preview(name = "432x792PortSimp", widthDp = 432, heightDp = 391)
@Preview(name = "432x792LandSimp", widthDp = 403, heightDp = 408)
annotation class DevicePreview432x792

//Pixel 3a API 30 5.6 1080x2220
@Preview(name = "392x735PortFull", widthDp = 392, heightDp = 735)
@Preview(name = "392x735LandFull", widthDp = 759, heightDp = 368)
@Preview(name = "392x735PortSimp", widthDp = 392, heightDp = 362)
@Preview(name = "392x735LandSimp", widthDp = 374, heightDp = 368)
annotation class DevicePreview392x735

//Pixel 3 XL API 29 6.3 1440x2960
@Preview(name = "411x748PortFull", widthDp = 411, heightDp = 748)
@Preview(name = "411x748LandFull", widthDp = 748, heightDp = 383)
@Preview(name = "411x748PortSimp", widthDp = 411, heightDp = 369)
@Preview(name = "411x748LandSimp", widthDp = 369, heightDp = 383)
annotation class DevicePreview411x748

//Pixel 3 API 30 5.46 1080x2160
@Preview(name = "392x713PortFull", widthDp = 392, heightDp = 713)
@Preview(name = "392x713LandFull", widthDp = 737, heightDp = 368)
@Preview(name = "392x713PortSimp", widthDp = 392, heightDp = 351)
@Preview(name = "392x713LandSimp", widthDp = 363, heightDp = 368)
annotation class DevicePreview392x713

//Pixel 2 XL API 29 5.99 1440x2880
@Preview(name = "411x746PortFull", widthDp = 411, heightDp = 746)
@Preview(name = "411x746LandFull", widthDp = 774, heightDp = 383)
@Preview(name = "411x746PortSimp", widthDp = 411, heightDp = 368)
@Preview(name = "411x746LandSimp", widthDp = 382, heightDp = 383)
annotation class DevicePreview411x746



@DevicePreview360x672
@DevicePreview320x402
@DevicePreview320x552
@DevicePreview480x776
@DevicePreview384x568
@DevicePreview360x568
@DevicePreview411x659
@DevicePreview600x889
@DevicePreview1024x696
@DevicePreview320x509
@DevicePreview900x1200
@DevicePreview800x1208
@DevicePreview411x842
@DevicePreview448x904
@DevicePreview411x814
@DevicePreview411x804
@DevicePreview411x819
@DevicePreview392x778
@DevicePreview393x777
@DevicePreview411x792
@DevicePreview392x718
@DevicePreview432x792
@DevicePreview392x735
@DevicePreview411x748
@DevicePreview392x713
@DevicePreview411x746
annotation class MultiDevicePreview

@Composable
fun logScreenSize() {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp

    Log.d("ScreenSize", "widthDp = $screenWidth, heightDp = $screenHeight")
}