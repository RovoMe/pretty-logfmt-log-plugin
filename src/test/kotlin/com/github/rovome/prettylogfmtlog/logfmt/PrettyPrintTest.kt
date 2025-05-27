package com.github.rovome.prettylogfmtlog.logfmt

import junit.framework.TestCase
class PrettyPrintTest : TestCase() {
    val prettyPrintIndicators = listOf(
        "[",
        "    key = value",
        "    anotherKey = \"another value\"",
        "    msg = \"Properties location [classpath:build.properties] not resolvable: class path resource [build.properties] cannot be opened because it does not exist\"",
        "    error = \"multi",
        "        line",
        "        error\"",
        "]",
    )

    val standardLogfmtLogLineIndicators = listOf(
            "key=value",
            "key1=value1 key2=value2",
            "level=info time=2024-09-06T12:00:00Z msg=\"Test message\"",
            "level=info time=2024-09-06T12:00:00Z user=\"john doe\" msg=\"Test message\" status=success"
    )

    val fullLog = listOf("[\"2025-05-22T14:20:05.374\"] ERROR: \"Redis connection error on queue 'queue:slack-notification-event-queue': Unable to connect to Redis. Retrying after a 10s delay.\" ",
            "[",
            "    time = \"2025-05-22T14:20:05.374\"",
            "    level = error",
            "    thread = ecosio-commands-1",
            "    package = com.ecosio.slack.components",
            "    module = EcosioCommandHandler",
            "    msg = \"Redis connection error on queue 'queue:slack-notification-event-queue': Unable to connect to Redis. Retrying after a 10s delay.\"",
            "    error = \"org.springframework.data.redis.RedisConnectionFailureException: Unable to connect to Redis",
            "    \tat org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory\$ExceptionTranslatingConnectionProvider.translateException(LettuceConnectionFactory.java:1858)",
            "    \tat org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory\$ExceptionTranslatingConnectionProvider.getConnection(LettuceConnectionFactory.java:1789)",
            "    \tat org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory\$SharedConnection.getNativeConnection(LettuceConnectionFactory.java:1586)",
            "    \tat org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory\$SharedConnection.lambda\$getConnection\$0(LettuceConnectionFactory.java:1566)",
            "    \tat org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory.doInLock(LettuceConnectionFactory.java:1527)",
            "    \tat org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory\$SharedConnection.getConnection(LettuceConnectionFactory.java:1563)",
            "    \tat org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory.getSharedConnection(LettuceConnectionFactory.java:1249)",
            "    \tat org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory.getConnection(LettuceConnectionFactory.java:1055)",
            "    \tat org.springframework.data.redis.core.RedisConnectionUtils.fetchConnection(RedisConnectionUtils.java:195)",
            "    \tat org.springframework.data.redis.core.RedisConnectionUtils.doGetConnection(RedisConnectionUtils.java:144)",
            "    \tat org.springframework.data.redis.core.RedisConnectionUtils.getConnection(RedisConnectionUtils.java:105)",
            "    \tat org.springframework.data.redis.core.RedisTemplate.execute(RedisTemplate.java:398)",
            "    \tat org.springframework.data.redis.core.RedisTemplate.execute(RedisTemplate.java:378)",
            "    \tat org.springframework.data.redis.core.AbstractOperations.execute(AbstractOperations.java:117)",
            "    \tat org.springframework.data.redis.core.DefaultListOperations.leftPop(DefaultListOperations.java:96)",
            "    \tat com.ecosio.slack.components.EcosioCommandHandler.lambda\$init\$0(EcosioCommandHandler.java:169)",

            "    \tat java.base/java.util.concurrent.Executors\$RunnableAdapter.call(Executors.java:572)",
            "    \tat java.base/java.util.concurrent.FutureTask.run(FutureTask.java:317)",
            "    \tat java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1144)",
            "    \tat java.base/java.util.concurrent.ThreadPoolExecutor\$Worker.run(ThreadPoolExecutor.java:642)",
            "    \tat java.base/java.lang.Thread.run(Thread.java:1583)",

            "    Caused by: org.springframework.data.redis.connection.PoolException: Could not get a resource from the pool",
            "    \tat org.springframework.data.redis.connection.lettuce.LettucePoolingConnectionProvider.getConnection(LettucePoolingConnectionProvider.java:114)",
            "    \tat org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory\$ExceptionTranslatingConnectionProvider.getConnection(LettuceConnectionFactory.java:1787)",
            "    \t... 19 common frames omitted",
            "    Caused by: io.lettuce.core.RedisConnectionException: Unable to connect to 127.0.0.1/<unresolved>:6379",
            "    \tat io.lettuce.core.RedisConnectionException.create(RedisConnectionException.java:63)",
            "    \tat io.lettuce.core.RedisConnectionException.create(RedisConnectionException.java:41)",
            "    \tat io.lettuce.core.AbstractRedisClient.getConnection(AbstractRedisClient.java:354)",
            "    \tat io.lettuce.core.RedisClient.connect(RedisClient.java:220)",
            "    \tat org.springframework.data.redis.connection.lettuce.StandaloneConnectionProvider.lambda\$getConnection\$1(StandaloneConnectionProvider.java:112)",
            "    \tat java.base/java.util.Optional.orElseGet(Optional.java:364)",
            "    \tat org.springframework.data.redis.connection.lettuce.StandaloneConnectionProvider.getConnection(StandaloneConnectionProvider.java:112)",
            "    \tat org.springframework.data.redis.connection.lettuce.LettucePoolingConnectionProvider.lambda\$getConnection\$0(LettucePoolingConnectionProvider.java:96)",
            "    \tat io.lettuce.core.support.ConnectionPoolSupport\$RedisPooledObjectFactory.create(ConnectionPoolSupport.java:209)",
            "    \tat io.lettuce.core.support.ConnectionPoolSupport\$RedisPooledObjectFactory.create(ConnectionPoolSupport.java:199)",
            "    \tat org.apache.commons.pool2.BasePooledObjectFactory.makeObject(BasePooledObjectFactory.java:68)",
            "    \tat org.apache.commons.pool2.impl.GenericObjectPool.create(GenericObjectPool.java:557)",
            "    \tat org.apache.commons.pool2.impl.GenericObjectPool.borrowObject(GenericObjectPool.java:299)",
            "    \tat org.apache.commons.pool2.impl.GenericObjectPool.borrowObject(GenericObjectPool.java:231)",
            "    \tat io.lettuce.core.support.ConnectionPoolSupport\$1.borrowObject(ConnectionPoolSupport.java:109)",
            "    \tat io.lettuce.core.support.ConnectionPoolSupport\$1.borrowObject(ConnectionPoolSupport.java:104)",
            "    \tat org.springframework.data.redis.connection.lettuce.LettucePoolingConnectionProvider.getConnection(LettucePoolingConnectionProvider.java:109)",
            "    \t... 20 common frames omitted",
            "    Caused by: io.netty.channel.AbstractChannel\$AnnotatedConnectException: Connection refused: /127.0.0.1:6379",
            "    Caused by: java.net.ConnectException: Connection refused",
            "    \tat java.base/sun.nio.ch.Net.pollConnect(Native Method)",
            "    \tat java.base/sun.nio.ch.Net.pollConnectNow(Net.java:682)",
            "    \tat java.base/sun.nio.ch.SocketChannelImpl.finishConnect(SocketChannelImpl.java:973)",
            "    \tat io.netty.channel.socket.nio.NioSocketChannel.doFinishConnect(NioSocketChannel.java:336)",
            "    \tat io.netty.channel.nio.AbstractNioChannel\$AbstractNioUnsafe.finishConnect(AbstractNioChannel.java:339)",
            "    \tat io.netty.channel.nio.NioEventLoop.processSelectedKey(NioEventLoop.java:784)",
            "    \tat io.netty.channel.nio.NioEventLoop.processSelectedKeysOptimized(NioEventLoop.java:732)",
            "    \tat io.netty.channel.nio.NioEventLoop.processSelectedKeys(NioEventLoop.java:658)",
            "    \tat io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:562)",
            "    \tat io.netty.util.concurrent.SingleThreadEventExecutor\$4.run(SingleThreadEventExecutor.java:998)",
            "    \tat io.netty.util.internal.ThreadExecutorMap\$2.run(ThreadExecutorMap.java:74)",
            "    \tat io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)",

            "    \tat java.base/java.lang.Thread.run(Thread.java:1583)",

            "    \"",

            "    rawError = \"org.springframework.data.redis.RedisConnectionFailureException: Unable to connect to Redis\\n\\tat org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory\$ExceptionTranslatingConnectionProvider.translateException(LettuceConnectionFactory.java:1858)\\n\\tat org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory\$ExceptionTranslatingConnectionProvider.getConnection(LettuceConnectionFactory.java:1789)\\n\\tat org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory\$SharedConnection.getNativeConnection(LettuceConnectionFactory.java:1586)\\n\\tat org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory\$SharedConnection.lambda\$getConnection\$0(LettuceConnectionFactory.java:1566)\\n\\tat org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory.doInLock(LettuceConnectionFactory.java:1527)\\n\\tat org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory\$SharedConnection.getConnection(LettuceConnectionFactory.java:1563)\\n\\tat org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory.getSharedConnection(LettuceConnectionFactory.java:1249)\\n\\tat org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory.getConnection(LettuceConnectionFactory.java:1055)\\n\\tat org.springframework.data.redis.core.RedisConnectionUtils.fetchConnection(RedisConnectionUtils.java:195)\\n\\tat org.springframework.data.redis.core.RedisConnectionUtils.doGetConnection(RedisConnectionUtils.java:144)\\n\\tat org.springframework.data.redis.core.RedisConnectionUtils.getConnection(RedisConnectionUtils.java:105)\\n\\tat org.springframework.data.redis.core.RedisTemplate.execute(RedisTemplate.java:398)\\n\\tat org.springframework.data.redis.core.RedisTemplate.execute(RedisTemplate.java:378)\\n\\tat org.springframework.data.redis.core.AbstractOperations.execute(AbstractOperations.java:117)\\n\\tat org.springframework.data.redis.core.DefaultListOperations.leftPop(DefaultListOperations.java:96)\\n\\tat com.ecosio.slack.components.EcosioCommandHandler.lambda\$init\$0(EcosioCommandHandler.java:169)\\n\\tat java.base/java.util.concurrent.Executors\$RunnableAdapter.call(Executors.java:572)\\n\\tat java.base/java.util.concurrent.FutureTask.run(FutureTask.java:317)\\n\\tat java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1144)\\n\\tat java.base/java.util.concurrent.ThreadPoolExecutor\$Worker.run(ThreadPoolExecutor.java:642)\\n\\tat java.base/java.lang.Thread.run(Thread.java:1583)\\nCaused by: org.springframework.data.redis.connection.PoolException: Could not get a resource from the pool\\n\\tat org.springframework.data.redis.connection.lettuce.LettucePoolingConnectionProvider.getConnection(LettucePoolingConnectionProvider.java:114)\\n\\tat org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory\$ExceptionTranslatingConnectionProvider.getConnection(LettuceConnectionFactory.java:1787)\\n\\t... 19 common frames omitted\\nCaused by: io.lettuce.core.RedisConnectionException: Unable to connect to 127.0.0.1/<unresolved>:6379\\n\\tat io.lettuce.core.RedisConnectionException.create(RedisConnectionException.java:63)\\n\\tat io.lettuce.core.RedisConnectionException.create(RedisConnectionException.java:41)\\n\\tat io.lettuce.core.AbstractRedisClient.getConnection(AbstractRedisClient.java:354)\\n\\tat io.lettuce.core.RedisClient.connect(RedisClient.java:220)\\n\\tat org.springframework.data.redis.connection.lettuce.StandaloneConnectionProvider.lambda\$getConnection\$1(StandaloneConnectionProvider.java:112)\\n\\tat java.base/java.util.Optional.orElseGet(Optional.java:364)\\n\\tat org.springframework.data.redis.connection.lettuce.StandaloneConnectionProvider.getConnection(StandaloneConnectionProvider.java:112)\\n\\tat org.springframework.data.redis.connection.lettuce.LettucePoolingConnectionProvider.lambda\$getConnection\$0(LettucePoolingConnectionProvider.java:96)\\n\\tat io.lettuce.core.support.ConnectionPoolSupport\$RedisPooledObjectFactory.create(ConnectionPoolSupport.java:209)\\n\\tat io.lettuce.core.support.ConnectionPoolSupport\$RedisPooledObjectFactory.create(ConnectionPoolSupport.java:199)\\n\\tat org.apache.commons.pool2.BasePooledObjectFactory.makeObject(BasePooledObjectFactory.java:68)\\n\\tat org.apache.commons.pool2.impl.GenericObjectPool.create(GenericObjectPool.java:557)\\n\\tat org.apache.commons.pool2.impl.GenericObjectPool.borrowObject(GenericObjectPool.java:299)\\n\\tat org.apache.commons.pool2.impl.GenericObjectPool.borrowObject(GenericObjectPool.java:231)\\n\\tat io.lettuce.core.support.ConnectionPoolSupport\$1.borrowObject(ConnectionPoolSupport.java:109)\\n\\tat io.lettuce.core.support.ConnectionPoolSupport\$1.borrowObject(ConnectionPoolSupport.java:104)\\n\\tat org.springframework.data.redis.connection.lettuce.LettucePoolingConnectionProvider.getConnection(LettucePoolingConnectionProvider.java:109)\\n\\t... 20 common frames omitted\\nCaused by: io.netty.channel.AbstractChannel\$AnnotatedConnectException: Connection refused: /127.0.0.1:6379\\nCaused by: java.net.ConnectException: Connection refused\\n\\tat java.base/sun.nio.ch.Net.pollConnect(Native Method)\\n\\tat java.base/sun.nio.ch.Net.pollConnectNow(Net.java:682)\\n\\tat java.base/sun.nio.ch.SocketChannelImpl.finishConnect(SocketChannelImpl.java:973)\\n\\tat io.netty.channel.socket.nio.NioSocketChannel.doFinishConnect(NioSocketChannel.java:336)\\n\\tat io.netty.channel.nio.AbstractNioChannel\$AbstractNioUnsafe.finishConnect(AbstractNioChannel.java:339)\\n\\tat io.netty.channel.nio.NioEventLoop.processSelectedKey(NioEventLoop.java:784)\\n\\tat io.netty.channel.nio.NioEventLoop.processSelectedKeysOptimized(NioEventLoop.java:732)\\n\\tat io.netty.channel.nio.NioEventLoop.processSelectedKeys(NioEventLoop.java:658)\\n\\tat io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:562)\\n\\tat io.netty.util.concurrent.SingleThreadEventExecutor\$4.run(SingleThreadEventExecutor.java:998)\\n\\tat io.netty.util.internal.ThreadExecutorMap\$2.run(ThreadExecutorMap.java:74)\\n\\tat io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)\\n\\tat java.base/java.lang.Thread.run(Thread.java:1583)\\n\"",

            "]"
    )

    fun testLogLinesThatShouldMatchAPrettyPrintedLogLine() {
        val tracker = LogFmtBlockStateTracker()
        prettyPrintIndicators.forEach {
            assertTrue("Expected match for input: '$it'", tracker.shouldFoldLine(it))
        }
    }

    fun testLogLinesThatDoNotMatchPrettyPrintedLogLInes() {
        val tracker = LogFmtBlockStateTracker()
        standardLogfmtLogLineIndicators.forEach {
            assertFalse("Expected no match for input: '$it'", tracker.shouldFoldLine(it))
        }
    }

    fun testLogLinesWithMultiLines() {
        val tracker = LogFmtBlockStateTracker()
        fullLog.forEachIndexed { index, line ->
            if (index == 0) {
                assertFalse("Expected match for input: '$line'", tracker.shouldFoldLine(line))
            } else {
                assertTrue("Expected match for input: '$line'", tracker.shouldFoldLine(line))
            }
        }
    }

    fun testPrettyPrinting() {
        val map = mutableMapOf<String, String>()
        map["level"] = "info"
        map["time"] = "2024-09-06T12:00:00Z"
        map["msg"] = "Test message"
        map["status"] = "success"

        val result = prettyPrintLogfmt(map)
        assertEquals("""[
    level = info
    time = 2024-09-06T12:00:00Z
    msg = Test message
    status = success
]
""", result)
    }
}