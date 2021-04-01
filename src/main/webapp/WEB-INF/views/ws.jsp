<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <!--common -->
<%@ include file="/WEB-INF/views/include/common.jsp"%>  
<title>Insert title here</title>
</head>
    <body>
        <div class="container">
            <div class="py-5 text-center">
                <h2>Basic Web socket</h2>
                <p class="lead">Sample of basic WebSocket broadcast - without STOMP & SockJS.</p>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <div class="row mb-3">
                        <div class="input-group">
                            Web socket connection:&nbsp;
                            <div class="btn-group">
                                <button type="button" id="connect" class="btn btn-sm btn-outline-secondary" onclick="connect()">Connect</button>
                            <button type="button" id="disconnect" class="btn btn-sm btn-outline-secondary" onclick="disconnect()" disabled>Disconnect</button>
                            </div>                        
                        </div>
                    </div>
                    <div class="row mb-3">
                        <div class="input-group" id="sendmessage" style="display: none;">
                            <input type="text" id="message" class="" placeholder="Message">
                            <div class="input-group-append">
                                <button id="send" class="btn btn-primary" onclick="send()">Send</button>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div id="content"></div>                    
                </div>
            </div>
        </div>
 
        <footer class="my-5 text-muted text-center text-small">
            <p class="mb-1">© 2020 Dariawan</p>
            <ul class="list-inline">
                <li class="list-inline-item"><a href="https://www.dariawan.com">Homepage</a></li>
                <li class="list-inline-item"><a href="#">Articles</a></li>
            </ul>
        </footer>
        
        <script>
            var ws;
            function setConnected(connected) {
                document.getElementById("connect")?.toggleAttribute("disabled");
                document.getElementById("disconnect")?.toggleAttribute("disabled");
                if (connected) {
                	document.getElementById("sendmessage").style.display='block';
                } else {
                	document.getElementById("sendmessage").style.display='hidden';
                }
            }
 
            function connect() {
                var url = 'ws://localhost:9080/portfolio/web-socket';

                ws = new WebSocket(url);
                ws.onopen = function () {
                    showBroadcastMessage('<div class="alert alert-success">Connected to server</div>');
                };
                ws.onmessage = function (data) {
                    showBroadcastMessage(createTextNode(data.data));
                };
                setConnected(true);
            }
 
            function disconnect() {
                if (ws != null) {
                    ws.close();
                    showBroadcastMessage('<div class="alert alert-warning">Disconnected from server</div>');
                }
                setConnected(false);
            }            
 
            function send() {
                ws.send(document.getElementById("message").value);
                document.getElementById("message").value = "";
            }
 
            function createTextNode(msg) {
                return '<div class="alert alert-info">' + msg + '</div>';
            }
            
            function showBroadcastMessage(message) {
                console.log('메세지수신됨!!:' + message)
            	document.getElementById("content").innerHTML= 
                	document.getElementById("content").innerHTML + message;
            }
        </script>
    </body>
</html>