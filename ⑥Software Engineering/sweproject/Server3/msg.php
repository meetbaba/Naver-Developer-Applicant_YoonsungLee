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
  $table_result = mysqli_query($conn, "SELECT * FROM ".$MATCHING_TABLE);

  // Matching Table에 100개 넣는거
  // for($i=0; $i<10; $i++){
  //   $num="12345678900".$i;
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
    case "100" :
    //NULL POST for response message.
        echo json_encode(checkMessageTable($MESSAGE_TABLE2));
        break;

    case "101" :
    //authorization request from mobile.
        $vehicle_code=$_POST['vehicle_code'];
        if(checkExistingVehicleCode($vehicle_code)){

          $tmp_result=checkRegisteredUserId($vehicle_code);
          if($tmp_result=="false"){
            // There is no registed user id to that vehicle_code.

            if(checkReservationTable($vehicle_code)){
              //This vehicle code exists in the Reservation table

              $message['flag']=(string)"299";
              $message['error']="Required vehicle code is already in authorization process.";
              echo json_encode($message);
            }else{
              //This vehicle code does not exist in the Reservation table
              $vehicle_code=$_POST['vehicle_code'];
              $user_id=$_POST['user_id'];
              $user_mac=$_POST['user_mac'];

              $flag=(string)"401";
              $num_contents=1;
              $content=array(0=>"user_id", 1=>$user_id);

              if(storeMessageTable($MESSAGE_TABLE4, $flag, $num_contents, $content)){
                // the storage success into meesage table 4
                echo json_encode(checkMessageTable($MESSAGE_TABLE2));

                if(storeReservationTable($vehicle_code, $user_id, $user_mac)){
                  //success to store to the Reservation table.

                }else{
                  // the storage failed
                  $message['flag']=(string)"299";
                  $message['error']="Reservation Store Error";
                  echo json_encode($message);
                }

              }else{
                // the storage failed
                $message['flag']=(string)"299";
                $message['error']="Message Store Error";
                echo json_encode($message);
              }



            }

          }else{
            //Mathed user id is already registered in the matching table.
            $message['flag']=(string)"299";
            $message['error']="User is already registered to vehicle_code '".$vehicle_code."'\n Check your vehicle code or user id again.";

            echo json_encode($message);
          }


        }else{
          // This vehicle code does not exist in the vehicle code list.
          $message['flag']=(string)"299";
          $message['error']="Vehicle Code '".$vehicle_code."' is not valid. \n Check your vehicle code again.";

          echo json_encode($message);
        }
      break;

    case "102" :
    //Preparation alarm message.
        $vehicle_code=$_POST['vehicle_code'];
        if(checkExistingVehicleCode($vehicle_code)){

          $tmp_result=checkRegisteredUserId($vehicle_code);
          if($tmp_result=="false"){
            // There is no registed user id to that vehicle_code.
            $message['flag']=(string)"299";
            $message['error']="There is no registered user to vehicle code.\n Check again your vehiclecode or user id";

            echo json_encode($message);

          }else{
            $flag=(string)"402";
            $num_contents=5;
            $content=array(0=>"user_id", 1=>$_POST['user_id'],
                            2=>"departure_time", 3=>$_POST['departure_time'],
                            4=>"destination", 5=>$_POST['destination'],
                            6=>"target_temperature", 7=>$_POST['target_temperature'],
                            8=>"keeping_time", 9=>$_POST['keeping_time']);


            if(storeMessageTable($MESSAGE_TABLE4, $flag, $num_contents, $content)){
              // the storage success into meesage table 4
              echo json_encode(checkMessageTable($MESSAGE_TABLE2));
            }else{
              // the storage failed
              $message['flag']=(string)"299";
              $message['error']="Store Error";
              echo json_encode($message);
            }
          }


        }else{
          $message['flag']=(string)"299";
          $message['error']="Vehicle Code '".$vehicle_code."' is not valid. \n Check your vehicle code again.";

          echo json_encode($message);
        }

        break;

    case "399" :
    //Error message from raspberrypi. Server has to post again to mobile.
        $message['flag']=(string)"299";
        $message['error']=$_POST['error'];

        echo json_encode($message);
        break;

    case "301" :
    //authorization result message from raspberrypi. Server has to post again to mobile.
    //According to result, server define whether the required user_id and user_mac are moved to the mathching table.
        $flag=(string)"201";
        $num_contents=3;
        $authorization_result=$_POST['authorization_result'];
        $vehicle_code=$_POST['vehicle_code'];
        $user_id=$_POST['user_id'];

        $content=array(0=>"authorization_result", 1=>$authorization_result,
                        2=>"vehicle_code", 3=>$vehicle_code,
                        4=>"user_id", 5=>$user_id);

        if(storeMessageTable($MESSAGE_TABLE2, $flag, $num_contents, $content)){
          // the storage success into meesage table 4
          echo json_encode(checkMessageTable($MESSAGE_TABLE4));
        }else{
          // the storage failed
          $message['flag']=(string)"499";
          $message['error']="Store Error";
          echo json_encode($message);
        }

        //put out the reservated data from reservation table, set matching table.
        if($authorization_result=="true"){
          $putout_result=putoutReservationTable($vehicle_code, $user_id);

          if($putout_result!="false"){
            $sql="UPDATE ".$MATCHING_TABLE." SET user_id='".$user_id."', user_mac='".$putout_result."' WHERE vehicle_code='".$vehicle_code."'";
            if(mysqli_query($conn, $sql)){
            }else{
            }
          }


        }else if($authorization_result=="false"){
          putoutReservationTable($vehicle_code, $user_id);
        }


        break;

    case "302" :
        $flag=(string)"202";
        $num_contents=1;
        $end_connection=$_POST['end_connection'];
        $user_id=$_POST['user_id'];

        $content=array(0=>"end_connection", 1=>$end_connection);

        if($end_connection=="true"){
          $sql="UPDATE ".$MATCHING_TABLE." SET user_id=NULL, user_mac=NULL WHERE user_id='".$user_id."'";
          mysqli_query($conn, $sql);
        }
        if(storeMessageTable($MESSAGE_TABLE2, $flag, $num_contents, $content)){
          // the storage success into meesage table 4
          echo json_encode(checkMessageTable($MESSAGE_TABLE4));
        }else{
          // the storage failed
          $message['flag']=(string)"499";
          $message['error']="Store Error";
          echo json_encode($message);
        }
        break;

    case "303" :
        $flag=(string)"203";
        $num_contents=2;
        $battery=$_POST['battery'];
        $temperature=$_POST['temperature'];

        $content=array(0=>"battery", 1=>$battery,
                        2=>"temperature", 3=>$temperature);
        if(storeMessageTable($MESSAGE_TABLE2, $flag, $num_contents, $content)){
          // the storage success into meesage table 4
          echo json_encode(checkMessageTable($MESSAGE_TABLE4));
        }else{
          // the storage failed
          $message['flag']=(string)"499";
          $message['error']="Store Error";
          echo json_encode($message);
        }

        break;
    case "300" :
        echo json_encode(checkMessageTable($MESSAGE_TABLE4));
        break;
  }



?>
