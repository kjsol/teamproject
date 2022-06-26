# teamproject
위 소스 파일은 자바 언어를 이용하여 만든 채팅 프로그램이며 서버와 클라이언트 파일로 구성되어 있습니다.  
작년 학기 수강한 컴퓨터네트워크 강의 내용 중 자바 채팅 프로그래밍을 공부하였던 경험을 통하여 이번 프로젝트를 진행하게 되었습니다.            
클라이언트 로그인 기능을 포함하여 대화말을 전송한 사람의 아이디를 대화말과 함께 출력하는 채팅 프로그램입니다.   
단, 클라이언트 프로그램은 반드시 먼저 아이디를 입력하고 대화말을 전송해야 합니다.  

##Description   
서버 미실행시 클라이언트에는 접속 시도 오류를 처리하였으며, 로그온 중인 클라이언트들에게만 채팅 메시지가 전송된다. 
로그온을 하게 되면 로그아웃 버튼이 생성되는데 이 버튼을 클릭하게 되면 로그아웃 메시지를 서버로 전송하여 로그아웃이 진행된다. 
또 한, class WinListener extends WindowAdapter 함수를 통해 로그아웃을 하지 않고 클라이언트 창을 닫게 되더라도 로그아웃이 된다. 
로그온/로그아웃 이벤트를 접속 중인 클라이언트들에게 알려주며, 로그온 중인 클라이언트 윈도우 종료시 소켓을 해제하여 서버는 접속 중인 클라이언트들에게 이벤트를 알려준다.

##Files

ChatMessageC2                      
     public ChatMessageC2(): 클라이언트 로그인 GUI 생성(로그온 아이디와 메시지 입력하는 필드 생성 등)            
     public void runClient(): socket을 생성하여 서버에 전송                 
     public void actionPerformed(ActionEvent ae)                    
     class WinListener extends WindowAdapter              
     public void keyPressed(KeyEvent ke)                

ChatMessageS2                      
    public ChatMessageS2(): 서버 GUI 생성                  
    public void runServer()                    
    class ServerThread extends Thread                      

# 팀에 대한 정보를 알려주세요.
