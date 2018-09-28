
package midtermproject;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Jonathan Anders
 */
public class Employee {

    private final SimpleStringProperty employeeId;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty title;
    private final SimpleStringProperty titleOfCourtesy;
    private final SimpleStringProperty birthDate;
    private final SimpleStringProperty hireDate;
    private final SimpleStringProperty address;
    private final SimpleStringProperty city;
    private final SimpleStringProperty region;
    private final SimpleStringProperty postalCode;
    private final SimpleStringProperty country;
    private final SimpleStringProperty homePhone;
    private final SimpleStringProperty extension;
    private final SimpleStringProperty photo;
    private final SimpleStringProperty notes;
    private final SimpleStringProperty reportsTo;
    
 
    Employee(String employeeId, String lastName, String firstName, String title, String titleOfCourtesy,
            String birthDate, String hireDate, String address, String city, String region, String postalCode, String country,
            String homePhone, String extension, String photo, String notes,
            String reportsTo)
    {
        this.employeeId = new SimpleStringProperty(employeeId);
        this.lastName = new SimpleStringProperty(lastName);
        this.firstName = new SimpleStringProperty(firstName);
        this.title = new SimpleStringProperty(title);
        this.titleOfCourtesy = new SimpleStringProperty(titleOfCourtesy);
        this.birthDate = new SimpleStringProperty(birthDate);
        this.hireDate = new SimpleStringProperty(hireDate);
        this.address = new SimpleStringProperty(address);
        this.city = new SimpleStringProperty(city);
        this.region = new SimpleStringProperty(region);
        this.postalCode = new SimpleStringProperty(postalCode);
        this.country = new SimpleStringProperty(country);
        this.homePhone = new SimpleStringProperty(homePhone);
        this.extension = new SimpleStringProperty(extension);
        this.photo = new SimpleStringProperty(photo);
        this.notes = new SimpleStringProperty(notes);
        this.reportsTo = new SimpleStringProperty(reportsTo);
    }
    
    public String getEmployeeId()
    {
        return employeeId.get();
    }
    
    public void setEmployeeId(String empId)
    {
        employeeId.set(empId);
    }
    
    public String getFirstName() {
        return firstName.get();
    }
    public void setFirstName(String fName) {
        firstName.set(fName);
    }
        
    public String getLastName() {
        return lastName.get();
    }
    public void setLastName(String fName) {
        lastName.set(fName);
    }
    
    public String getTitle()
    {
        return title.get();
    }

    public String gettitleOfCourtesy()
    {
        return titleOfCourtesy.get();
    }
    public String gettitle()
    {
          return title.get();
    }
    public String getBirthDate()
    {
         return birthDate.get();
    }
    public String getHireDate()
    {
        return hireDate.get();
    }
    public String getAddress()
    {
       return address.get();
    }
    public String getCity()
    {
      return city.get();
    }
    public String getRegion()
    {
       return region.get();
    }
    public String getPostalCode()
    {
        return postalCode.get();
    }
    public String getCountry()
    {
        return country.get();
    }
    public String getHomePhone()
    {
        return homePhone.get();
    }
    public String getExtension()
    {
        return extension.get();
    }
    public String getReportsTo()
    {
        return reportsTo.get();
    }

    
}
