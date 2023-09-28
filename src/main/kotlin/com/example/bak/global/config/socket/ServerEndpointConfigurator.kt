package com.example.bak.global.config.socket

import org.springframework.beans.BeansException
import org.springframework.beans.factory.BeanFactory
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.annotation.Configuration
import javax.websocket.server.ServerEndpointConfig


@Configuration
class ServerEndpointConfigurator : ServerEndpointConfig.Configurator(), ApplicationContextAware {
    companion object {
        @Volatile
        private var context: BeanFactory? = null
    }

    @Throws(InstantiationException::class)
    override fun <T> getEndpointInstance(clazz: Class<T>): T {
        return context!!.getBean(clazz)
    }

    @Throws(BeansException::class)
    override fun setApplicationContext(applicationContext: ApplicationContext) {
        ServerEndpointConfigurator.context = applicationContext
    }
}