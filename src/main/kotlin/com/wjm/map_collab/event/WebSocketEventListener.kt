package com.wjm.map_collab.event

import org.springframework.context.event.EventListener
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.stereotype.Component
import org.springframework.web.socket.messaging.SessionSubscribeEvent
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent

@Component
class WebSocketEventListener {
//    val subscriptions = mutableMapOf<String, MutableList<String>>()
//    private val destinations = mutableMapOf<String, String>()
//
//    // Listener for subscription events
//    @EventListener
//    fun handleSubscription(event: SessionSubscribeEvent) {
//        val headerAccessor = StompHeaderAccessor.wrap(event.message)
//        val destination = headerAccessor.destination ?: "Unknown"
//        val userName = headerAccessor.sessionId ?: "Anonymous"
//        subscriptions[destination] = (mutableListOf(userName) + (subscriptions[destination] ?: mutableListOf())).toMutableList()
//        destinations[userName] = destination
//
//        println("User $userName subscribed to $destination")
//        println("Subscriptions: $subscriptions")
//    }
//
//    // Listener for unsubscription events
//    @EventListener
//    fun handleUnsubscribe(event: SessionUnsubscribeEvent) {
//        val headerAccessor = StompHeaderAccessor.wrap(event.message)
//        val userName = headerAccessor.sessionId ?: "Anonymous"
//        val destination = destinations[userName] ?: "Unknown"
//        destinations.remove(userName)
//        subscriptions[destination] = (subscriptions[destination] ?: mutableListOf()).filter { it != userName }.toMutableList()
//
//        println("User $userName unsubscribed from $destination")
//        println("UnSubscriptions: $subscriptions")
//    }
}
