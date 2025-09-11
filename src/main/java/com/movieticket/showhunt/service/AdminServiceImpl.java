package com.movieticket.showhunt.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.movieticket.showhunt.entity.Admin;
import com.movieticket.showhunt.entity.Movie;
import com.movieticket.showhunt.entity.TheatreOwner;
import com.movieticket.showhunt.entity.User;
import com.movieticket.showhunt.repository.AdminRepository;
import com.movieticket.showhunt.repository.MovieRepository;
import com.movieticket.showhunt.repository.TheatreOwnerRepository;
import com.movieticket.showhunt.repository.UserRepository;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Service
public class AdminServiceImpl implements AdminService
{
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TheatreOwnerRepository ownerRepository;
	@Autowired
	private MovieRepository movieRepository;
	
	
	@Value("${aws.accessKeyId}")
    private String accessKey;

    @Value("${aws.secretAccessKey}")
    private String secretKey;

    @Value("${aws.region}")
    private String region;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    private S3Client createS3Client() {
        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey, secretKey)))
                .build();
    }

	
	
	@Override
	public Admin checkAdminLogin(String username,String password)
	{
		return adminRepository.findByUsernameAndPassword(username, password);
	}
	@Override
	public List<User> viewallusers()
	{
		return userRepository.findAll();
	}
	@Override
	public List<TheatreOwner> viewalltheatreowners()
	{
		return ownerRepository.findAll();
	}
	@Override
	public TheatreOwner gettheatreowner(int id)
	{
		return ownerRepository.findById(id).get();
	}
	@Override
	public List<Movie> viewallmovies()
	{
		return movieRepository.findAll();
	}
	@Override
	public String addtheatreowner(TheatreOwner owner)
	{
		ownerRepository.save(owner);
		return "Added Successfully";
	}
	 
	    public String uploadFile(MultipartFile file) throws IOException {
	        S3Client s3 = createS3Client();
	        String uniqueFileName = "posters/" + UUID.randomUUID() + "-" + file.getOriginalFilename();

	        try {
	            s3.putObject(PutObjectRequest.builder()
	                            .bucket(bucketName)
	                            .key(uniqueFileName)
	                            .contentType(file.getContentType())
	                            .build(),
	                    software.amazon.awssdk.core.sync.RequestBody.fromBytes(file.getBytes()));

	            return "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + uniqueFileName;

	        } catch (S3Exception e) {
	            throw new RuntimeException("Failed to upload file to S3: " + e.awsErrorDetails().errorMessage());
	        }
	    }
	    @Override
	    public Movie addMovie(Movie movie, MultipartFile posterFile) throws IOException {
	        if (posterFile != null && !posterFile.isEmpty()) {
	            String posterUrl = uploadFile(posterFile);
	            movie.setMovieposter(posterUrl);
	            movie.setTrailerUrl(movie.getTrailerUrl().replace(".com/watch?v=", "-nocookie.com/embed/"));
	        }
	        return movieRepository.save(movie);
	    }
	    @Override
	    public String updatetheatreownerstatus(int id)
	    {
	    	TheatreOwner owner=ownerRepository.findById(id).get();
	    	owner.setStatus(!owner.isStatus());
	    	ownerRepository.save(owner);
	    	return "Updated Status";
	    }
	    @Override
	    public String updatetheatreowner(TheatreOwner to)
	    {
	    	TheatreOwner owner=ownerRepository.findById(to.getId()).get();
	    	owner.setTheatrename(to.getTheatrename());
	    	owner.setEmail(to.getEmail());
	    	owner.setContact(to.getContact());
	    	owner.setBankname(to.getBankname());
	    	owner.setIfsccode(to.getIfsccode());
	    	owner.setAddress(to.getAddress());
	    	ownerRepository.save(owner);
	    	return "Updated Status";
	    }
}
