package com.github.krystiankowalik.transactionlistprovider.controller

import com.github.krystiankowalik.transactionlistprovider.service.UrlService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.SocketException
import java.net.UnknownHostException

@RestController
@RequestMapping("/url")
class UrlController(val service: UrlService) {

    @GetMapping
    fun getUrl(): String = service.getLocalUrl()
}
