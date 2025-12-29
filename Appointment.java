package ra.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Appointment {
    private String appointmentId;
    private String patientName;
    private String phoneNumber;
    private LocalDate appointmentDate;
    private String doctor;

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Appointment() {
    }

    public Appointment(String appointmentId, String patientName, String phoneNumber, LocalDate appointmentDate, String doctor) {
        this.appointmentId = appointmentId;
        this.patientName = patientName;
        this.phoneNumber = phoneNumber;
        this.appointmentDate = appointmentDate;
        this.doctor = doctor;
    }
    public String getAppointmentId() { return appointmentId; }
    public void setAppointmentId(String appointmentId) { this.appointmentId = appointmentId; }
    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public LocalDate getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDate appointmentDate) { this.appointmentDate = appointmentDate; }
    public String getDoctor() { return doctor; }
    public void setDoctor(String doctor) { this.doctor = doctor; }

    @Override
    public String toString() {
        return String.format("ID: %-6s | BN: %-20s | SĐT: %-12s | Ngày: %-12s | BS: %s",
                appointmentId, patientName, phoneNumber, appointmentDate.format(DATE_FORMATTER), doctor);
    }

    public void inputData(Scanner scanner, List<Appointment> existingAppointments) {
        while (true) {
            System.out.print("mã lịch hẹn: ");
            String inputId = scanner.nextLine().trim();
            if (inputId.length() == 6) {
                boolean isExist = existingAppointments.stream()
                        .anyMatch(a -> a.getAppointmentId().equals(inputId));
                if (!isExist) {
                    this.appointmentId = inputId;
                    break;
                } else {
                    System.err.println("đã tồn tại");
                }
            } else {
                System.err.println("phải có đúng 6 ký tự");
            }
        }

        while (true) {
            System.out.print("Nhập tên bệnh nhân : ");
            String inputName = scanner.nextLine().trim();
            if (inputName.length() >= 10 && inputName.length() <= 50) {
                this.patientName = inputName;
                break;
            } else {
                System.err.println("Tphải từ 10 đến 50 ký tự");
            }
        }

        while (true) {
            System.out.print("Nhập số điện thoại (VN): ");
            String inputPhone = scanner.nextLine().trim();
            if (inputPhone.matches("^0\\d{9}$")) {
                this.phoneNumber = inputPhone;
                break;
            } else {
                System.err.println("sdt không hợp lệ .");
            }
        }


        while (true) {
            System.out.print("Nhập ngày : ");
            String dateStr = scanner.nextLine().trim();
            try {
                this.appointmentDate = LocalDate.parse(dateStr, DATE_FORMATTER);
                if (this.appointmentDate.isBefore(LocalDate.now())) {
                    System.err.println("ko hop le");
                } else {
                    break;
                }
            } catch (DateTimeParseException e) {
                System.err.println("sai format");
            }
        }

        while (true) {
            System.out.print("Nhập tên bác sĩ phụ trách: ");
            String inputDoc = scanner.nextLine().trim();
            if (!inputDoc.isEmpty() && inputDoc.length() <= 200) {
                this.doctor = inputDoc;
                break;
            } else {
                System.err.println("Tên bác sĩ không được để trống và tối đa 200 ký tự.");
            }
        }
    }
}
