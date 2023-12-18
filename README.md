# Veteriner Yönetim Sistemi

## Başka Dillerde Oku :books:

- :tr: [Türkçe](README.md)
- :uk: [English](README_EN.md)

Veteriner Yönetim Sistemi, bir veteriner kliniğinin işlerini yönetmesine yardımcı olan bir RESTful API'dir. Bu API, veteriner doktorları, müşteriler, hayvanlar, aşılar ve randevular dahil olmak üzere çeşitli kaynakları yönetmek için endpoint'ler sağlar.

### Teknolojiler

<p align="center">
    <img src="https://skillicons.dev/icons?i=spring,maven,git,github,postgresql,idea,postman,java" height="75" />
</p>

### Başlıca Özellikler

- Veteriner doktorları kaydetme, güncelleme, görüntüleme ve silme
- Doktorların müsait günlerini kaydetme, güncelleme, görüntüleme ve silme
- Müşterileri kaydetme, güncelleme, görüntüleme ve silme
- Müşterilere ait hayvanları kaydetme, güncelleme, görüntüleme ve silme
- Hayvanlara uygulanan aşıları kaydetme, güncelleme, görüntüleme ve silme
- Hayvanlar için veteriner hekimlere randevu oluşturma, güncelleme, görüntüleme ve silme

### Proje Ekran Görüntüleri

*Entity Relationship Diagram*

<img src="src/media/entity_relationship_diagram.svg" alt="Diagram_1" width="" />

*Postman Koleksiyonu https://www.postman.com/bpolatt/workspace/vet-clinic-rest-api/overview*

<img src="src/media/postmen_collection.png" alt="Diagram_2" width="" />

*Database Yapısı ve İçindeki Örnek Veriler*

<img src="src/media/database.png" alt="Diagram_3" width="" />
<img src="src/media/customers.png" alt="Diagram_4" width="" />
<img src="src/media/animals.png" alt="Diagram_5" width="" />
<img src="src/media/vaccines.png" alt="Diagram_6" width="" />
<img src="src/media/doctors.png" alt="Diagram_7" width="" />
<img src="src/media/availableDates.png" alt="Diagram_8" width="" />
<img src="src/media/appointments.png" alt="Diagram_9" width="" />

### API Endpoint'leri

### Veteriner Doktorları Yönetme

- `POST http://localhost:8080/v1/doctors`: Yeni bir doktor oluşturur.
- `GET http://localhost:8080/v1/doctors/1`: Belirli bir ID'ye sahip doktoru alır.
- `GET http://localhost:8080/v1/doctors?page=0&size=3`: Sayfalama ve sıralama yaparak doktorları alır.
- `PUT http://localhost:8080/v1/doctors`: Bir doktoru günceller.
- `DELETE http://localhost:8080/v1/doctors/1`: Belirli bir ID'ye sahip doktoru siler.

### Doktorların Müsait Günlerini Yönetme

- `POST http://localhost:8080/v1/available-dates`: Yeni bir müsait tarih oluşturur.
- `GET http://localhost:8080/v1/available-dates/1`: Belirli bir ID'ye sahip müsait tarihi alır.
- `GET http://localhost:8080/v1/available-dates?page=0&size=3`: Sayfalama ve sıralama yaparak müsait tarihleri alır.
- `PUT http://localhost:8080/v1/available-dates`: Bir müsait tarihi günceller.
- `DELETE http://localhost:8080/v1/available-dates/1`: Belirli bir ID'ye sahip müsait tarihi siler.

### Müşterileri Yönetme

- `POST http://localhost:8080/v1/customers`: Yeni bir müşteri oluşturur.
- `GET http://localhost:8080/v1/customers/1`: Belirli bir ID'ye sahip müşteriyi alır.
- `GET http://localhost:8080/v1/customers?page=0&size=3`: Sayfalama ve sıralama yaparak müşterileri alır.
- `GET http://localhost:8080/v1/customers/1/animals`: Belirli bir ID'ye sahip müşterinin hayvanlarını alır.
- `GET http://localhost:8080/v1/customers/filterByName?name=ahmet`: Müşterileri isimlerine göre filtreler.
- `PUT http://localhost:8080/v1/customers`: Bir müşteriyi günceller.
- `DELETE http://localhost:8080/v1/customers/1`: Belirli bir ID'ye sahip müşteriyi siler.

### Müşterilere Ait Hayvanları Yönetme

- `POST http://localhost:8080/v1/animals`: Yeni bir hayvan oluşturur.
- `GET http://localhost:8080/v1/animals/1`: Belirli bir ID'ye sahip hayvanı alır.
- `GET http://localhost:8080/v1/animals?page=0&size=3`: Sayfalama ve sıralama yaparak hayvanları alır.
- `GET http://localhost:8080/v1/animals/1/customer`: Belirli bir ID'ye sahip hayvanın sahibini alır.
- `GET http://localhost:8080/v1/animals/filter?name=Peluş`: Hayvanları isimlerine göre filtreler.
- `GET http://localhost:8080/v1/animals/vaccines?startDate=2022-01-01&endDate=2024-12-31`: Tarih aralığına göre hayvanlara uygulanan aşıları alır.
- `PUT http://localhost:8080/v1/animals/1`: Belirli bir ID'ye sahip hayvanı günceller.
- `DELETE http://localhost:8080/v1/animals/1`: Belirli bir ID'ye sahip hayvanı siler.

### Hayvanlara Uygulanan Aşıları Yönetme

- `POST http://localhost:8080/v1/vaccines`: Yeni bir aşı oluşturur.
- `GET http://localhost:8080/v1/vaccines/1`: Belirli bir ID'ye sahip aşıyı alır.
- `GET http://localhost:8080/v1/vaccines?page=0&size=3`: Sayfalama ve sıralama yaparak aşıları alır.
- `GET http://localhost:8080/v1/vaccines/animal/1 `: Belirli bir ID'ye sahip hayvana uygulanan aşıları alır.
- `GET http://localhost:8080/v1/vaccines/protection-dates?start_date=2023-12-31&end_date=2024-12-31`: Girilen tarih aralığına göre aşı koruma tarihlerini alır.
- `PUT http://localhost:8080/v1/vaccines`: Bir aşıyı günceller.
- `DELETE http://localhost:8080/v1/vaccines/1`: Belirli bir ID'ye sahip aşıyı siler.
- `PUT http://localhost:8080/v1/vaccines/vaccinate`: Bir hayvana aşı uygular.

### Hayvanlar İçin Veteriner Hekimlere Randevu Oluşturma

- `POST http://localhost:8080/v1/appointments/create`: Bir hayvana randevu oluşturur.
- `GET http://localhost:8080/v1/appointments/1`: Belirli bir ID'ye sahip randevuyu alır.
- `GET http://localhost:8080/v1/appointments?page=0&size=3`: Sayfalama ve sıralama yaparak randevuları alır.
- `GET http://localhost:8080/v1/appointments/doctor/1?start_date_time=2023-01-01T10:00:00&end_date_time=2023-05-01T11:00:00`: Belirli bir ID'ye sahip doktorun belirli bir tarih aralığındaki randevularını alır.
- `GET http://localhost:8080/v1/appointments/animal/2?start_date_time=2023-01-01T10:00:00&end_date_time=2023-02-01T11:00:00`: Belirli bir ID'ye sahip hayvanın belirli bir tarih aralığındaki randevularını alır.
- `PUT http://localhost:8080/v1/appointments`: Bir randevuyu günceller.
- `DELETE http://localhost:8080/v1/appointments/1`: Belirli bir ID'ye sahip randevuyu siler.

### Veritabanı Yapısı

Veritabanı altı ana varlık içerir: `Doktor`, `UygunTarih`, `Müşteri`, `Hayvan`, `Aşı`, ve `Randevu`.

- `Doktor`: Klinikteki veterinerleri temsil eder. Her doktorun bir `UygunTarih` ve `Randevu` listesi bulunur.
- `UygunTarih`: Bir doktorun uygun tarihlerini temsil eder. Her uygun tarih bir `Doktor` ile ilişkilidir.
- `Müşteri`: Kliniğin müşterilerini temsil eder. Her müşterinin bir `Hayvan` listesi bulunur.
- `Hayvan`: Müşterilere ait hayvanları temsil eder. Her hayvan bir `Müşteri` ile ilişkilidir ve bir `Aşı` ve `Randevu` listesine sahiptir.
- `Aşı`: Hayvanlara uygulanan aşıları temsil eder. Her aşı bir `Hayvan` ile ilişkilidir.
- `Randevu`: Hayvanların bir doktora görünmesi için yapılan randevuları temsil eder. Her randevu bir `Doktor` ve bir `Hayvan` ile ilişkilidir.


### Kurulum
1. Bu projeyi klonlayın.
2. Proje içindeki `vetclinic.sql` dosyasını kullanarak postgresql veritabanını oluşturun.
3. `src/main/resources/application.properties` dosyasını açın ve veritabanı bağlantı bilgilerini güncelleyin.
4. https://www.postman.com/bpolatt/workspace/vet-clinic-rest-api/overview adresinden Postman koleksiyonunu indirin.
5. Postman koleksiyonunu Postman'e import edin.
6. Postman koleksiyonundaki istekleri kullanarak API'yi test edin.


### Katkıda Bulunma

Bu proje açık kaynaklıdır ve katkılarınıza açıktır. Katkıda bulunmak için lütfen aşağıdaki adımları izleyin:

1. Projeyi Forklayın
2. Kendi Fork'unuzda bir Feature Branch oluşturun (`git checkout -b feature/AmazingFeature`)
3. Değişikliklerinizi Commit edin (`git commit -m 'Add some AmazingFeature'`)
4. Branch'ı Push edin (`git push origin feature/AmazingFeature`)
5. Pull Request oluşturun

### License

Bu proje MIT lisansı ile lisanslanmıştır. Daha fazla bilgi için [LICENSE](LICENSE) dosyasına bakın.

### İletişim

<p align="center">
  <a href="https://github.com/Bpolat0">
    <img src="https://skillicons.dev/icons?i=github" height="75" />

  <a href="https://discord.com/users/m.batuhanpolat">
    <img src="https://skillicons.dev/icons?i=discord" height="75" />

  <a href="https://www.linkedin.com/in/mehmetbatuhanpolat/">
    <img src="https://skillicons.dev/icons?i=linkedin" height="75" />
  </a>
  <a href="https://app.patika.dev/bpolat">
    <img alt="Qries" src="https://patika-prod.s3.eu-central-1.amazonaws.com/staticFiles/patikaLogo.png"
         width="90" height="90">
</p>
