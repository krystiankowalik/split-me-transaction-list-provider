package com.github.krystiankowalik.transactionlistprovider.service

import org.springframework.stereotype.Service
import java.net.DatagramSocket
import java.net.InetAddress

@Service
class UrlService {

    fun getLocalUrl():String {
        var ip="lklk"
        DatagramSocket().use { socket ->
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002)
            ip = socket.localAddress.hostAddress
        }
        return ip
    }
}