package Repository;

public class CarreraRepository {
    /**
     * Reads a CSV file and inserts new person records into the database.
     * Each row in the CSV should contain person details, including an address ID.
     *
     * @param urlFile the file path or URL of the CSV file to be processed.
     * @throws IOException if an error occurs while reading the CSV file.
     * @throws CsvValidationException if the CSV format is invalid or data validation fails.
     */
    public void insertPersonsFromCSV(String urlFile) throws IOException, CsvValidationException {
        EntityManager em = JPAUtil.getEntityManager();
        CSVReader reader = new CSVReader(new FileReader(urlFile));
        String[] line;
        reader.readNext();

        em.getTransaction().begin();

        while((line = reader.readNext()) != null){
            Person person = new Person();

            person.setName(line[0]);
            person.setAge(Integer.parseInt(line[1]));
            person.setAddress(em.find(Address.class, Integer.parseInt(line[2])));
            if(!line[3].equals("null"))
                person.setPartner(em.find(Partner.class, Integer.parseInt(line[3])));
            else
                person.setPartner(null);

            em.persist(person);
        }

        em.getTransaction().commit();
        System.out.print("\nPeople added successfully");
        em.close();
    }
}
