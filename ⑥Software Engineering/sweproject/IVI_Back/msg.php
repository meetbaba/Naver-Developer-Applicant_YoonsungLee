<?php
include 'statics.php';
include 'swe_functions.php';
set_time_limit(0);
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: GET, PUT, POST, DELETE, OPTIONS');
header('Access-Control-Max-Age: 3800');
header('Access-Control-Allow-Headers: Content-Type, Authorization, X-Requested-With');



/*
  This is the receiver class for communication server.
  Clients will send the data to this address.
  This receive it and send response message to sender after interpret that message.

  Constraints
  1. The message will be posted using 'POST' function defined in REST.
  2. Every posted message should follow the message format we predefined.

  If the received message do not follow upper constraints, we do not care anything in case of that.

*/

//connect to DB and table.
  $conn=mysqli_connect($HOST, $DB_USER, $DB_PASSWORD);
  mysqli_select_db($conn, $DB_NAME);

  // Matching Table에 100개 넣는거
  // for($i=; $i<100; $i++){
  //   $num="1234567890".$i;
  //   $address="192.168.1.".$i;
  //   echo $num."/".$address;
  //   $sql="INSERT INTO ".$MATCHING_TABLE." (vehicle_code,raspberrypi_ip) VALUES('".$num."','".$address."')";
  //   if(mysqli_query($conn, $sql)){
  //     echo "success : ".$i."\n";
  //   }else{
  //     echo "fail : ".$i."\n";
  //   };
  //
  // }

  $flag=$_POST['flag'];


  switch($flag){
    case "500" :
    //NULL POST for response message from Raspberry pi.
        echo json_encode(checkMessageTable($MESSAGE_TABLE6));
        break;

    case "501" :
    //authorization request from Raspberry pi.
        $user_id=$_POST['user_id'];

        $flag=(string)"11";
        $num_contents=1;
        $content=array(0=>"user_id", 1=>$user_id);

        if(storeMessageTable($MESSAGE_TABLE1, $flag, $num_contents, $content)){
          // the storage success into meesage table 4
          echo json_encode(checkMessageTable($MESSAGE_TABLE6));
        }else{
          // the storage failed
          $message['flag']=(string)"699";
          $message['error']="Store Error";
          echo json_encode($message);
        }
      break;

    case "502" :
    //Preparation alarm message.
        $departure_time=$_POST['departure_time'];
        $destination=$_POST['destination'];
        $target_temperature=$_POST['target_temperature'];
        $keeping_time=$_POST['keeping_time'];

        $flag=(string)"12";
        $num_contents=4;
        $content=array(0=>"departure_time", 1=>$departure_time,
                        2=>"destination", 3=>$destination,
                        4=>"target_temperature", 5=>$target_temperature,
                        6=>"keeping_time", 7=>$keeping_time);

        if(storeMessageTable($MESSAGE_TABLE1, $flag, $num_contents, $content)){
          // the storage success into meesage table 4
          echo json_encode(checkMessageTable($MESSAGE_TABLE6));
        }else{
          // the storage failed
          $message['flag']=(string)"699";
          $message['error']="Store Error";
          echo json_encode($message);
        }

        break;

    case "599" :
        //Error message from raspberrypi. Server has to post again to mobile.

            $error=$_POST['error'];

            $flag=(string)"19";
            $num_contents=1;
            $content=array(0=>"error", 1=>$error);

            if(storeMessageTable($MESSAGE_TABLE1, $flag, $num_contents, $content)){
              // the storage success into meesage table 4
              echo json_encode(checkMessageTable($MESSAGE_TABLE6));
            }else{
              // the storage failed
              $message['flag']=(string)"699";
              $message['error']="Store Error";
              echo json_encode($message);
            }
            break;

    case "700" :
    //NULL POST for response message from ECU Simulator.
        echo json_encode(checkMessageTable($MESSAGE_TABLE8));
        break;

    case "701" :
    //The case that ECU Simulator send the datas(battery, temperature, seating).
        $battery=$_POST['battery'];
        $temperature=$_POST['temperature'];
        $seating=$_POST['seating'];

        $flag=(string)"13";
        $num_contents=3;
        $content=array(0=>"battery", 1=>$battery,
                        2=>"temperature", 3=>$temperature,
                        4=>"seating", 5=>$seating);

        if(storeMessageTable($MESSAGE_TABLE1, $flag, $num_contents, $content)){
          // the storage success into meesage table 4
          echo json_encode(checkMessageTable($MESSAGE_TABLE8));
        }else{
          // the storage failed
          $message['flag']=(string)"899";
          $message['error']="Store Error";
          echo json_encode($message);
        }

        break;

    case "799" :
        //Error message from raspberrypi. Server has to post again to mobile.

            $error=$_POST['error'];

            $flag=(string)"699";
            $num_contents=1;
            $content=array(0=>"error", 1=>$error);

            if(storeMessageTable($MESSAGE_TABLE6, $flag, $num_contents, $content)){
              // the storage success into meesage table 4
              echo json_encode(checkMessageTable($MESSAGE_TABLE8));
            }else{
              // the storage failed
              $message['flag']=(string)"899";
              $message['error']="Store Error";
              echo json_encode($message);
            }
            break;

    case "00" :
    //NULL POST for response message from JS.
        echo json_encode(checkMessageTable($MESSAGE_TABLE1));
        break;

    case "01" :
    //send message "authorization_result" to Rasberry pi.

        $authorization_result=$_POST['authorization_result'];
        $user_id=$_POST['user_id'];
        //$vehicle_code=$_POST['vehicle_code'];
        $flag=(string)"601";
        $num_contents=2;

        $content=array(0=>"authorization_result", 1=>$authorization_result,
                      2=>"user_id", 3=>$user_id);
        if(storeMessageTable($MESSAGE_TABLE6, $flag, $num_contents, $content)){
          // the storage success into meesage table 4
          echo json_encode(checkMessageTable($MESSAGE_TABLE1));
        }else{
          // the storage failed
          $message['flag']=(string)"19";
          $message['error']="Store Error";
          echo json_encode($message);
        }

        break;
    case "02" :
    //send message "end_connection" to Rasberry pi.
        $end_connection=$_POST['end_connection'];
        $flag=(string)"602";
        $num_contents=1;

        $content=array(0=>"end_connection", 1=>$end_connection);
        if(storeMessageTable($MESSAGE_TABLE6, $flag, $num_contents, $content)){
          // the storage success into meesage table 4
          echo json_encode(checkMessageTable($MESSAGE_TABLE1));
        }else{
          // the storage failed
          $message['flag']=(string)"19";
          $message['error']="Store Error";
          echo json_encode($message);
        }

        break;

    case "03" :
    //send message "vehicle status" to Rasberry pi.
        $battery=$_POST['battery'];
        $temperature=$_POST['temperature'];

        $flag=(string)"603";
        $num_contents=2;

        $content=array(0=>"battery", 1=>$battery,
                        2=>"temperature", 3=>$temperature);

        if(storeMessageTable($MESSAGE_TABLE6, $flag, $num_contents, $content)){
          // the storage success into meesage table 4
          echo json_encode(checkMessageTable($MESSAGE_TABLE1));
        }else{
          // the storage failed
          $message['flag']=(string)"19";
          $message['error']="Store Error";
          echo json_encode($message);
        }

        break;

    case "04" :
    //send message "action command" to ECU simulator.
        $airconditioner=$_POST['airconditioner'];
        $target_temperature=$_POST['target_temperature'];

        $flag=(string)"801";
        $num_contents=2;

        $content=array(0=>"airconditioner", 1=>$airconditioner,
                        2=>"target_temperature", 3=>$target_temperature);

        if(storeMessageTable($MESSAGE_TABLE8, $flag, $num_contents, $content)){
          // the storage success into meesage table 4
          echo json_encode(checkMessageTable($MESSAGE_TABLE1));
        }else{
          // the storage failed
          $message['flag']=(string)"19";
          $message['error']="Store Error";
          echo json_encode($message);
        }

        break;



  }



?>
