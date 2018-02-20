var state = 0;
var counter = 0;

var STATE_TURN = 0;
var STATE_SETTLE = 1;
var STATE_MOVE_FORWARD = 2;
var STATE_SETTLE_2 = 3;
var STATE_TURN_2 = 4;
var STATE_SHOOT = 5;

var TURN_ANGLE_RIGHT = 25;
var TURN_ANGLE_LEFT = 18;
var TURN_SPEED = .5;

var SETTLE_TICKS = 20;

var DISTANCE_HIGH_SPEED = 60; //inches
var DISTANCE_TO_AUTOLINE = 115; //inches
var HIGH_SPEED = -.4;
var LOW_SPEED = -.3;

function init() {
	drivetrain.resetEncoders();
};

function periodic() {
	if (state == STATE_TURN) {
		if (gameData.length == 3 && gameData.charAt(0) == 'R') {
			if (drivetrain.getHeading() < TURN_ANGLE_RIGHT) {
				drivetrain.arcadeDrive(0, TURN_SPEED);
			} else {
				drivetrain.arcadeDrive(0, 0);
				counter = 0;
				state = STATE_SETTLE;
			}
		} else if (gameData.length == 3 && gameData.charAt(0) == 'L') {
			if (drivetrain.getHeading() > -TURN_ANGLE_LEFT) {
				drivetrain.arcadeDrive(0, -TURN_SPEED);
			} else {
				drivetrain.arcadeDrive(0, 0);
				counter = 0;
				state = STATE_SETTLE;
			}
		}
	} else if (state == STATE_SETTLE) {
		counter++;

		drivetrain.arcadeDrive(0, 0);

		if (counter > SETTLE_TICKS) {
			drivetrain.resetEncoders();
			state = STATE_MOVE_FORWARD;
		}
	} else if (state == STATE_MOVE_FORWARD) {
		if (drivetrain.getLeftEncoderDistance() < DISTANCE_HIGH_SPEED) {
			drivetrain.tankDrive(HIGH_SPEED, HIGH_SPEED);
			arm.middle();
		} else if (drivetrain.getLeftEncoderDistance() < DISTANCE_TO_AUTOLINE) {
			drivetrain.tankDrive(LOW_SPEED, LOW_SPEED);
		} else {
			drivetrain.tankDrive(0, 0);
			counter = 0;
			state = STATE_SETTLE_2;
		}
	} else if (state == STATE_SETTLE_2) {
		counter++;

		drivetrain.arcadeDrive(0, 0);

		if (counter > SETTLE_TICKS) {
			state = STATE_TURN_2;
			drivetrain.resetGyro();
		}
	} else if (state == STATE_TURN_2) {
		if (gameData.length == 3 && gameData.charAt(0) == 'R') {
			if (drivetrain.getHeading() > -TURN_ANGLE_RIGHT) {
				drivetrain.arcadeDrive(0, -TURN_SPEED);
			} else {
				drivetrain.arcadeDrive(0, 0);
				counter = 0;
				state = STATE_SHOOT;
			}
		} else if (gameData.length == 3 && gameData.charAt(0) == 'L') {
			if (drivetrain.getHeading() < TURN_ANGLE_LEFT) {
				drivetrain.arcadeDrive(0, TURN_SPEED);
			} else {
				drivetrain.arcadeDrive(0, 0);
				counter = 0;
				state = STATE_SHOOT;
			}
		}
	} else if (state == STATE_SHOOT) {
		intake.shoot();
		drivetrain.tankDrive(-.3, -.3);
	}
}