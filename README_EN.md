# Veteriner Yönetim Sistemi

[English](#english) | [Türkçe](#turkish)

Veterinary Management System is a RESTful API that helps manage the operations of a veterinary clinic. This API provides endpoints to manage various resources, including veterinary doctors, customers, animals, vaccines, and appointments.
## <a name="english"></a>English

### Technologies

- Java 11
- Spring Boot
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven

### Main Features

- Managing veterinarians: saving, updating, viewing, and deleting
- Managing the available days of doctors: saving, updating, viewing, and deleting
- Managing customers: saving, updating, viewing, and deleting
- Managing animals belonging to customers: saving, updating, viewing, and deleting
- Managing vaccines applied to animals: saving, updating, viewing, and deleting
- Creating appointments for animals to veterinarians: saving, updating, viewing, and deleting

### API Endpoints

#### Managing Veterinarians

- `POST /v1/doctors`: Creates a new doctor.
- `GET /v1/doctors/{id}`: Retrieves a doctor with a specific ID.
- `PUT /v1/doctors`: Updates a doctor.
- `DELETE /v1/doctors/{id}`: Deletes a doctor with a specific ID.

#### Managing the Available Days of Doctors

- `POST /v1/available-dates`: Creates a new available date.
- `GET /v1/available-dates/{id}`: Retrieves an available date with a specific ID.
- `PUT /v1/available-dates`: Updates an available date.
- `DELETE /v1/available-dates/{id}`: Deletes an available date with a specific ID.

#### Managing Customers

- `POST /v1/customers`: Creates a new customer.
- `GET /v1/customers/{id}`: Retrieves a customer with a specific ID.
- `PUT /v1/customers`: Updates a customer.
- `DELETE /v1/customers/{id}`: Deletes a customer with a specific ID.

#### Managing Animals Belonging to Customers

- `POST /v1/animals`: Creates a new animal.
- `GET /v1/animals/{id}`: Retrieves an animal with a specific ID.
- `PUT /v1/animals`: Updates an animal.
- `DELETE /v1/animals/{id}`: Deletes an animal with a specific ID.

#### Managing Vaccines Applied to Animals

- `POST /v1/vaccines`: Creates a new vaccine.
- `GET /v1/vaccines/{id}`: Retrieves a vaccine with a specific ID.
- `PUT /v1/vaccines`: Updates a vaccine.
- `DELETE /v1/vaccines/{id}`: Deletes a vaccine with a specific ID.

#### Creating Appointments for Animals to Veterinarians

- `POST /v1/appointments/create`: Creates a new appointment.
- `GET /v1/appointments/{id}`: Retrieves an appointment with a specific ID.
- `PUT /v1/appointments`: Updates an appointment.
- `DELETE /v1/appointments/{id}`: Deletes an appointment with a specific ID.
- `GET /v1/appointments/doctor/{doctorId}`: Retrieves appointments for a specific doctor within a specific date range.
- `GET /v1/appointments/animal/{animalId}`: Retrieves appointments for a specific animal within a specific date range.

### Database Structure

The database consists of six main entities: `Doctor`, `AvailableDate`, `Customer`, `Animal`, `Vaccine`, and `Appointment`.

- `Doctor`: Represents the veterinarians in the clinic. Each doctor has a list of `AvailableDate` and `Appointment`.
- `AvailableDate`: Represents the available dates of a doctor. Each available date is associated with a `Doctor`.
- `Customer`: Represents the customers of the clinic. Each customer has a list of `Animal`.
- `Animal`: Represents the animals owned by customers. Each animal is associated with a `Customer` and has a list of `Vaccine` and `Appointment`.
- `Vaccine`: Represents the vaccines applied to animals. Each vaccine is associated with an `Animal`.
- `Appointment`: Represents the appointments for animals to see a doctor. Each appointment is associated with a `Doctor` and an `Animal`.

### Installation

1. Clone the project to your local machine.
2. Edit the `application.properties` file according to your database settings.
3. Compile and run the project with Maven.

### Contributing

This project is open source and contributions are welcome. To contribute, please follow the steps below:

1. Fork the project
2. Create a Feature Branch in your own Fork (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push the Branch (`git push origin feature/AmazingFeature`)
5. Create a Pull Request

### License

This project is licensed under the MIT license. For more information, see the `LICENSE` file.
