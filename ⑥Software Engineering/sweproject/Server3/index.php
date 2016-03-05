<?php
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: GET, PUT, POST, DELETE, OPTIONS');
header('Access-Control-Max-Age: 3800');
header('Access-Control-Allow-Headers: Content-Type, Authorization, X-Requested-With');

$conn=mysqli_connect("localhost", "sweyoon", password12);
mysqli_select_db($conn, 'sweyoon');

$result = mysqli_query($conn, "SELECT * FROM sample");

function checkDBAlreadyExisting($dbConnection, $indexName, $data){

  while($row = mysqli_fetch_assoc($dbConnection)){
    if($row[$indexName]==$data){
      return $row['id'];
    }
  }
  return 0;
}


$flag=$_POST['flag'];
if($flag=="01"){
  $vehicle_code=$_POST['vehicle_code'];
  $raspberrypi_ip=$_POST['raspberrypi_ip'];
  $user_id=$_POST['user_id'];
  $user_mac=$_POST['user_mac'];

  $post_contents=$vehicle_code."</br>".$raspberrypi_ip."</br>".$user_id."</br>".$user_mac;
  $response["vehicle_code"]=$vehicle_code;
  $response["raspberrypi_ip"]=$raspberrypi_ip;
  $response["user_id"]=$user_id;
  $response["user_mac"]=$user_mac;

  $existing_code=checkDBAlreadyExisting($result, "vehicle_code", $vehicle_code);

  if($existing_code==0){
    $sql = "INSERT INTO sample (vehicle_code,raspberrypi_ip,user_id,user_mac) VALUES('".$vehicle_code."', '".$raspberrypi_ip."', '".$user_id."', '".$user_mac."')";
    $sqlResult = mysqli_query($conn, $sql);
    echo json_encode($response);
  }else{
    $alreadyResponse["vehicle_code"]="already exist : ".$existing_code;
    echo json_encode($alreadyResponse);
  }

}




?>
