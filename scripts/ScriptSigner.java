import java.io.*;
import java.nio.file.*;
import java.security.*;
import java.security.spec.*;

public class ScriptSigner {
	public static void main(String[] args) throws Exception {
		if (args[0].equals("sign")) {
			sign(args[2], new File(args[1]));
		} else if (args[0].equals("verify")) {
			verify(args[2], new File (args[1]));
		} else {
			System.out.println("Usage: sign privateKeyFile filename OR verify publicKeyFile filename");
		}

	}

	private static void genKey() {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
			SecureRandom random = SecureRandom.getInstanceStrong();

			keyGen.initialize(2048, random);

			KeyPair pair = keyGen.generateKeyPair();
			PrivateKey priv = pair.getPrivate();
			PublicKey pub = pair.getPublic();

			FileOutputStream publicKeyFile = new FileOutputStream("key.pub");
			FileOutputStream privateKeyFile = new FileOutputStream("key.priv");
			publicKeyFile.write(pub.getEncoded());
			privateKeyFile.write(priv.getEncoded());

		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	private static PublicKey loadPublicKey(File publicKey) throws Exception {
		byte[] keyFileBytes = Files.readAllBytes(publicKey.toPath());

		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyFileBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePublic(spec);
	}

	private static PrivateKey loadPrivateKey(File privateKey) throws Exception {
		byte[] keyFileBytes = Files.readAllBytes(privateKey.toPath());

		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyFileBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePrivate(spec);
	}

	private static void sign(String filename, File privateKeyFile) throws Exception {
		PrivateKey privKey = loadPrivateKey(privateKeyFile);

		Signature signatureGenerator = Signature.getInstance("SHA256withRSA");
		signatureGenerator.initSign(privKey);
		signatureGenerator.update(Files.readAllBytes(new File(filename).toPath()));

		byte[] signature = signatureGenerator.sign();

		FileOutputStream out = new FileOutputStream(filename.replaceAll("\\.js", "\\.sig"));
		out.write(signature);
		out.close();
	}

	private static void verify(String filename, File publicKeyFile) throws Exception {
		PublicKey publicKey = loadPublicKey(publicKeyFile);

		Signature signatureGenerator = Signature.getInstance("SHA256withRSA");
		signatureGenerator.initVerify(publicKey);
		signatureGenerator.update(Files.readAllBytes(new File(filename).toPath()));

		System.out.println(signatureGenerator.verify(Files.readAllBytes(new File("sig").toPath())));
	}
}