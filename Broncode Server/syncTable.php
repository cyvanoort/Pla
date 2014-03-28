<?php
	$table = $_POST["table"];

	$host = "localhost";
	$username = "carstpb26_plab";
	$password = "PixelBroS";
	$dbname = "carstpb26_plab";

	$con=mysqli_connect($host, $username, $password, $dbname);

	// Check connection
	if (mysqli_connect_errno($con)){
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
	}else{
		$result = mysqli_query($con, "SELECT * FROM ".$table.";");
	}

	$return_arr = array();
	
	if($table == "Boards"){
		while($row = mysqli_fetch_array($result)){
		  		$row_array['id'] = $row['id'];
				$row_array['lng'] = $row['lng'];
				$row_array['lat'] = $row['lat'];

				array_push($return_arr,$row_array);
		  }
	}else if($table == "Intresses"){
		while($row = mysqli_fetch_array($result)){
		  		$row_array['id'] = $row['id'];
				$row_array['categorie'] = $row['categorie'];

				array_push($return_arr,$row_array);
		  }
	}

	mysqli_close($con);

	echo json_encode($return_arr);
?>
