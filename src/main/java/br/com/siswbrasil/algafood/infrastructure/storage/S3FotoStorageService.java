package br.com.siswbrasil.algafood.infrastructure.storage;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import br.com.siswbrasil.algafood.core.storage.StorageProperties;
import br.com.siswbrasil.algafood.domain.service.FotoStorageService;

@Service
public class S3FotoStorageService implements FotoStorageService {

	@Autowired
	private AmazonS3 amazonS3;
	
	@Autowired
	private StorageProperties storageProperties;
	
	@Override
	public InputStream recuperar(String nomeArquivo) {
		return null;
	}

	@Override
	public void armazenar(NovaFoto novaFoto) {		
		try {
			String caminhoArquivo = getCaminhoArquivo(novaFoto.getNomeAquivo());
			
			System.out.println("caminhoArquivo: "+ caminhoArquivo);
			
			var objectMetadata = new ObjectMetadata();
			objectMetadata.setContentType(novaFoto.getContentType());
			System.out.println("ContentType: "+novaFoto.getContentType());
			System.out.println("Size: "+novaFoto.getTamanho());
			objectMetadata.setContentLength(novaFoto.getTamanho());
			
			var putObjectRequest = new PutObjectRequest(
					storageProperties.getS3().getBucket(),
					caminhoArquivo,
					novaFoto.getInputStream(),
					objectMetadata)
				.withCannedAcl(CannedAccessControlList.PublicRead);
			
			amazonS3.putObject(putObjectRequest);
		} catch (Exception e) {
			throw new StorageException("Não foi possível enviar arquivo para Amazon S3.", e);
		}
	}

	private String getCaminhoArquivo(String nomeArquivo) {
		return String.format("%s/%s", storageProperties.getS3().getDiretorioFotos(), nomeArquivo);
	}

	@Override
	public void remover(String nomeArquivo) {
	}

}