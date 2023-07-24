/** 함수들 나열 */

 function allCheck() {
			var id = document.getElementById('id');
			/** div 안에 있는 id값을 가져온다 */
			var pw = document.getElementById('pw');
			var confirm = document.getElementById('confirm');
			var userName = document.getElementById('userName');
			
			
			if(id.value == ""){
				alert('아이디는 필수 항목입니다.');
			}
			else if(pw.value == ""){
				alert('비밀번호는 필수 항목입니다.');
			}
			else if(confirm.value == ""){
				alert('비밀번호 확인은 필수 항목입니다.');
			}
			else if(userName.value == ""){
				alert('이름은 필수 항목입니다.');
			}
			else if(document.getElementById('email').value == ""){
				alert('이메일은 필수 항목입니다.');
			}
			
			else if(document.getElementById('authBtn').value == ""){
				alert('인증번호 필수 항목입니다.');
			}
			
			else{
				var f = document.getElementById('f');
				f.submit();
			}
			
			
			
		}
		
 function pwCheck() {
			pw = document.getElementById('pw');
			confirm = document.getElementById('confirm');
			label = document.getElementById('label');
					
			
			if(pw.value == confirm.value){
				label.innerHTML = '일치'
				/** 대소문자 지키는것이 좋다 */
				
			}
			else{
				label.innerHTML = '불일치'
			}
			/**window.alert('pwCheck 호출');*/
		}	
		
		
 function loginCheck(){
	  
	 		var id = document.getElementById('id');
			/** div 안에 있는 id값을 가져온다 */
			var pw = document.getElementById('pw');
			
			if(id.value == ""){
				alert('아이디는 필수 항목입니다.');
			}
			else if(pw.value == ""){
				alert('비밀번호는 필수 항목입니다.');
			}
			
			else{
				var f = document.getElementById('f');
				f.submit();
			}
}			
	

function updateButton(){
			var id = document.getElementById('id');
			if( id.value == ''){
				alert('아이디는 필수로 입력 값이 있어야합니다.')
				return ;
			}
			//alert('아이디 : ' + id.value);
			
			var pw = document.getElementById('pw');
			if( pw.value == ''){
				alert('비밀번호는 필수로 입력 값이 있어야합니다.')
				return ;
			}
			var confirmPw = document.getElementById('confirmPw');
			if( pw.value != confirmPw.value){
				alert('두 비밀번호는 같은 비밀번호로 입력하세요.')
				return ;
			}
			
			var formRef = document.getElementById('f')
			formRef.submit();
		}
		
		function cancelButton(){
			location.href='index.jsp';
		}
		
		
		
	
		
		function board_loginCheck(){
			var id = document.getElementById('id');
			if( id.value == ''){
				alert('로그인 후 이용가능합니다')
				location.href='login.jsp';
			}
			else{
				location.href='/jsp_exam/session_board/writeForm.jsp';
			}
		}
		
		function titleC_contentC(){
			var title = document.getElementById('title');
			var content = document.getElementById('content');
			if(title.value == ''){
				alert('제목을 작성해주세요')
				retun;
			}
			if(content.value == ''){
				alert('내용을 작성해주세요')
				retun;
			}
		}
		
		
		

		
	