package bam;

public class Analisis 
{
	public static void main (String[] args) throws InterruptedException
	{
		for(int i = 0; i<args.length; i++)
		{
			Thread enricher = new Thread(new Enricher(args[i]), "TextRazor");		
			enricher.start();
			
			Thread sintaxer = new Thread(new Sintaxer(args[i]), "MeaningCloud");		
			sintaxer.start();
			
			sintaxer.join();
			enricher.join();
		}
	}
}
