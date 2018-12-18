### SSH框架之上传图片到项目文件夹下并在前端显示  
1.前端jsp页面上传代码  
 
 ```jsp
   <form action="sendMessage.action"  method="post" enctype="multipart/form-data">
    <label>类别</label>
       <select name="category">
         <option value="1">书籍</option>
         <option value="2">服饰</option>
         <option value="3">证件</option>
         <option value="4">其他</option>
       </select><br>
       <label>描述</label>
       <input type="text" name="content"><br>
       <label>图片</label>
       <input type="file" name="upload"><br>
       <input type="submit" value="发布">
    </form>
 ``` 
 2.后台action将上传的图片保存，将保存的图片路径存入数据库  
 ```java
public String send() {
        //保存图片，返回路径
		String path=messageService.savePhoto(upload);
		User user=(User) ActionContext.getContext().getSession().get("user");
		Message message=new Message();
		message.setMessageUserid(user.getUserId()+"");
		message.setMessagePhoto("images/"+path);
		message.setMessageCategotyid(1);
		message.setMessageDescription(content);
		message.setMessageDate(new Date());
		//存入数据库
		messageService.saveMessage(message);
		return "success";
	}
```
3.保存图片的方法  
```java
public String savePhoto(File upload) {
		// TODO Auto-generated method stub
		//生成随机字符串
		String sources="2549hgtflkjadsf89nbvcMNBVCxZwe5989iuytFFJHGfDASwERtrty";
		Random random=new Random();
		StringBuilder stringBuilder=new StringBuilder();
		for(int i=0;i<5;i++) {
			stringBuilder.append(sources.charAt(random.nextInt(sources.length()))+"");
		}
		String str=stringBuilder.toString();
		
		String path="D:\\JavaWeb\\eclipse\\workspace\\lostandfoundSSH3\\WebContent\\images\\"+str+".jpg";

        //2.保存照片
        File file=new File(path);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return "0";
            }
        }
        if(upload==null) {
        	System.out.println("upload is null");
        }
        if(!file.exists()) {
        	System.out.println("file is null");
        }
        try{
            FileUtils.copyFile(upload,file);
        }catch (IOException e){
            e.printStackTrace();
            return "0";
        }
        return str+".jpg";
	}
```  
4.通过查询数据库获取图片路径，将路径传给前端，显示图片  
```jsp
 <c:forEach var="post" items="${postList}">
        <div>
           <img class="avatar" alt="头像" src="images/myLove.jpg">
           <label><c:out value="${post.getUser().getUserName()}" /></label><br>
       </div>
     
         <div class="content">  
             <a href="#"><c:out value="${post.getMessageDescription()}" /></a>
         </div>
         <div>
           <img class="content-img" alt="img-content" src="${ post.getMessagePhoto()}">
         </div>
         <div >
           <a href="#"><img class="comment-img" alt="comment" src="images/comment.png"></a> 
          
         </div>
         <div class="date">
             <c:out value="${post.getMessageDate()}" /><br>
         </div>
         <div class="div"></div>
      </c:forEach>
```
5.因为图片存储在项目文件下，当上传图片成功后，即使刷新页面前端也不能显示图片，需要刷新一下项目文件，再次刷新web页面才能显示图片  


 
