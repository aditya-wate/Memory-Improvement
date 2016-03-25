<%php
	$host="localhost";
	$port=3306;
	$socket="";
	$user="root";
	$password="wstwbh57";
	$dbname="memory_improve";
	
	$username = $_POST["username"];
	$password = $_POST["password"];
	$firstname = $_POST["firstname"];
	$lastname = $_POST["lastname"];
	
	$con = new mysqli($host, $user, $password, $dbname, $port, $socket)
		or die ('Could not connect to the database server' . mysqli_connect_error());
		
	$statement = mysqli_prepare($con, "INSERT INTO user (username, password_hash, first_name, last_name) VALUES (?,?,?,?)");
	mysqli_stmt_bind_param($statement, "ssss", $username, $password, $firstname, $lastname);
	mysqli_stmt_execute($statement);
	
	mysqli_stmt_close($statement);
	
	$con->close();
%>