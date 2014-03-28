<?php
// Create connection
$link=mysqli_connect("localhost","username","password","database");

// Check connection
if (mysqli_connect_errno($link))
  {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }
  
?>