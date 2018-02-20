var DISTANCE_HIGH_SPEED = 40; //inches
var DISTANCE_TO_AUTOLINE = 120; //inches
var HIGH_SPEED = -.8;
var LOW_SPEED = -.3;

function init() {
	drivetrain.resetEncoders();
};

function periodic() {
	if (drivetrain.getLeftEncoderDistance() < DISTANCE_HIGH_SPEED) {
		drivetrain.tankDrive(HIGH_SPEED, HIGH_SPEED);
	} else if (drivetrain.getLeftEncoderDistance() < DISTANCE_TO_AUTOLINE) {
		drivetrain.tankDrive(LOW_SPEED, LOW_SPEED);
	} else {
		drivetrain.tankDrive(0, 0);
	}
}