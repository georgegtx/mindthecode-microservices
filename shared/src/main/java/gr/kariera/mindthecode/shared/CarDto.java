package gr.kariera.mindthecode.shared;

public class CarDto {

        private Long id;
        private int mileage;
        private String maker;
        private String serialNumber;

        public CarDto(){ }

        public CarDto(Long id, int mileage, String maker, String serialNumber) {
            this.id = id;
            this.mileage = mileage;
            this.maker = maker;
            this.serialNumber = serialNumber;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public void setMileage(int mileage) {
            this.mileage = mileage;
        }

        public void setMaker(String maker) {
            this.maker = maker;
        }

        public void setSerialNumber(String serialNumber) {
            this.serialNumber = serialNumber;
        }

        public Long getId() {
            return id;
        }

        public int getMileage() {
            return mileage;
        }


        public String getMaker() {
            return maker;
        }


        public String getSerialNumber() {
            return serialNumber;
        }
}
