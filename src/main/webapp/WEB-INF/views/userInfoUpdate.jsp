<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<meta charset="UTF-8">
<title>회원 정보 수정</title>
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<style>
      
</style>
</head>
<body>
<jsp:include page="loginBox.jsp"></jsp:include>
    <h3>회원 정보 수정</h3>
    <form method="post" action="userinfoupdate.do" enctype="multipart/form-data">
        <table>
	        <tr>
	        	<td>
					<img width="200" src="/photo/${user.photoName}"/>
				</td>
				<td><input type="file" name="photo"/></td>
	        </tr>
            <tr>
                <th>아이디</th>
                <td>
                    <input type="text" id="userId" name="userId" value="${user.userId}" readonly/>
                </td>
            </tr>
            <tr>
                <th>닉네임</th>
                <td>
                    <input type="text" id="nickName" name="nickName" value="${user.nickName}"/>
                    <button id="nickChk" type="button">중복 확인</button>
                    <td id="nickMsg"></td>
                </td>
            </tr>
            <tr>
                <th>비밀번호</th>
                <td><input type="text" id="userPw" name="userPw" value="${user.userPw}"/></td>
            </tr>
            <tr>
                <th>키</th>
                <td><input type="text" id="height" name="height" value="${user.height}"/></td>
            </tr>
            <tr>
                <th>이름</th>
                <td>
                    <input type="text" id="userName" name="userName" value="${user.userName}" readonly/>               
                </td>            
            </tr>
            <tr>
                <th>포지션</th>
                <td>
                    <input id="posi" type="text" value="${user.position}" readonly/>
                    <select name="position" id="position">
		            	 <option value="센터">센터</option>
				         <option value="포워드">포워드</option>
				         <option value="가드">가드</option>
				         <option value="무관">무관</option>
	      			</select>               
                </td>        
            </tr>
            <tr>
            	<th>생년월일</th>               
            	<td><input type="text" id ="birthday" value="${user.birthday}" readonly/></td>
         	</tr>
            <tr>
                <th>선호 경기 지역</th>
                <td>
                    <input type="text" id="location" name="location" value="${user.gu}" readonly/>
                    <button type="button" id="address_kakao">검색</button>
                </td>
            </tr>
            <tr>
                <th>E-MAIL</th>
                <td><input type="email" id="email" name="email" value="${user.email}"/></td>
            </tr>
            <tr>
                <th>선호 경기 시간</th>
                <td>
                	<input type="text" id="favT" name="favTime" value="${user.favTime}" readonly/>
	                <select name="favTime" id="favTime">
	            	 <option value="평일 오전">평일 오전</option>
			         <option value="평일 오후">평일 오후</option>
			         <option value="주말 오전">주말 오전</option>
			         <option value="주말 오후">주말 오후</option>
	      			</select>
      			</td>
            </tr>
            <tr>      
                <th colspan="2">
                    <button type="submit" id="update">수정 완료</button>
                    <button onclick="location.href='userinfo.go'">취소</button>
                </th>
            </tr>
        </table>
    </form>
</body>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>

var userNickName = '${user.nickName}';
console.log(userNickName);

document.getElementById("address_kakao").addEventListener("click", function(){ 
    //주소검색 버튼을 클릭하면 카카오 지도 발생
     new daum.Postcode({
         oncomplete: function(data) { //선택시 입력값 세팅
             var sigungu = data.sigungu; // '구' 주소 넣기
             document.getElementById("location").value = sigungu;
             
         }
     }).open();
 });
 
$('#nickChk').on('click',function(e){
	   
	   var nickName = $('#nickName').val();
	   
	   $.ajax({
	      type:'post',
	      url:'nickChk.ajax',
	      data:{nickName:nickName},
	      dataType:'json',
	      success:function(data){
	    	  console.log(data);
	         if(data.nickChk == 0){
	            console.log('사용 가능한 닉네임');
	            $('#nickMsg').css({'font-size':'8px', 'color':'darkgreen'});
	            $('#nickMsg').html('사용 가능한 닉네임 입니다.');
	            $('#update').attr('disabled', false);
	    
	         }else{
	            console.log('이미 사용 중인 닉네임');
	            $('#nickMsg').css({'font-size':'8px', 'color':'red'});
	            $('#nickMsg').html('이미 사용 중인 닉네임 입니다.');
	            $('#update').attr('disabled', true);
	            
	            if(userNickName == $('#nickName').val()) {
	            	$('#nickMsg').css({'font-size':'8px', 'color':'darkgreen'});
		            $('#nickMsg').html('사용 가능한 닉네임 입니다.');
		            $('#update').attr('disabled', false);
	            }
	         }
	      },
	      error:function(e){
	         console.log(e);
	      }
	   });
	});
	
$('#position').change(function(){
	$('#posi').val($(this).val());
});

$('#favTime').change(function(){
	$('#favT').val($(this).val());
});
	
</script>
</html>