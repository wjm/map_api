package com.wjm.map_collab.controller

import com.wjm.map_collab.entity.DrawMessage
import com.wjm.map_collab.event.WebSocketEventListener
import com.wjm.map_collab.service.MapDrawingService
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller


@Controller
class WsController(
    private val messagingTemplate: SimpMessagingTemplate,
    private val webSocketEventListener: WebSocketEventListener,
    private val mapDrawingService: MapDrawingService
) {

    @MessageMapping("/map/{map_id}")
    @SendTo("/map/{map_id}")
    fun sendMessage(
        @DestinationVariable("map_id") mapId: String,
        message: DrawMessage,
        headerAccessor: SimpMessageHeaderAccessor
    ) {
        val sessionId = headerAccessor.sessionId
        var actions = arrayOf("create", "update", "delete", "selectionchange")
        if (message.type in actions) {
            println("Processing ${message.type} operation for message from $sessionId")
            when (message.type) {
                "create" -> {
                    message.data?.let { mapDrawingService.insertDrawings(it, mapId) }
                }
                "update" -> {
                    message.data?.let { mapDrawingService.insertDrawings(it, mapId) }
                }
                "delete" -> {
                    message.data?.let { mapDrawingService.deleteDrawings(it) }
                }
                "selectionchange" -> {
                    // Handle selection change
                }
            }
            if (message.type == "update") {

            }
        }
        //return message
    }


}