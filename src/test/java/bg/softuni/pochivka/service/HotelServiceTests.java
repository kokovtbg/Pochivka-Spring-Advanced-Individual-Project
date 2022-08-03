package bg.softuni.pochivka.service;

import bg.softuni.pochivka.model.dto.*;
import bg.softuni.pochivka.model.entity.*;
import bg.softuni.pochivka.model.enums.*;
import bg.softuni.pochivka.model.mapper.HotelMapper;
import bg.softuni.pochivka.repository.*;
import bg.softuni.pochivka.service.HotelService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class HotelServiceTests {
    private HotelRepository mockedHotelRepository;
    private ComfortRepository mockedComfortRepository;
    private TownRepository mockedTownRepository;
    private RoomRepository mockedRoomRepository;
    private UserRepository mockedUserRepository;
    private HotelService hotelService;
    private HotelMapper hotelMapper;
    private List<Hotel> testHotels;
    private List<HotelViewDTO> testHotelViews;
    private List<Hotel> noStarHotels;
    private List<Hotel> hotelsNotDistinct;
    private List<Hotel> noStarHotelNotDistinct;

    @BeforeEach
    public void init() {
        this.mockedHotelRepository = Mockito.mock(HotelRepository.class);
        this.mockedComfortRepository = Mockito.mock(ComfortRepository.class);
        this.mockedTownRepository = Mockito.mock(TownRepository.class);
        this.mockedRoomRepository = Mockito.mock(RoomRepository.class);
        this.mockedUserRepository = Mockito.mock(UserRepository.class);
        this.hotelMapper = new HotelMapper();
        this.hotelService = new HotelService(mockedHotelRepository, mockedComfortRepository,
                mockedTownRepository, mockedRoomRepository, mockedUserRepository, hotelMapper);
        this.testHotels = new ArrayList<>();
        this.testHotelViews = new ArrayList<>();
        this.noStarHotels = new ArrayList<>();
        this.hotelsNotDistinct = new ArrayList<>();
        this.noStarHotelNotDistinct = new ArrayList<>();
    }

    //Name Empty
    @Test
    public void getAllByNameAndAccommodationAndCategoryAndComfort_nameEmptyAndTownNullAndComfortsNotEmpty() {
        List<Accommodation> accommodations = new ArrayList<>();
        List<HotelCategory> categories = new ArrayList<>();
        List<Comfort> comforts = this.mockedComfortRepository.findAll();

        HotelSearchDTO hotelSearchDTO = new HotelSearchDTO();
        hotelSearchDTO.setName("");
        hotelSearchDTO.setAccommodation(new ArrayList<>());
        hotelSearchDTO.setCategory(new ArrayList<>());
        hotelSearchDTO.setTownName("noTown");
        hotelSearchDTO.setComfort(new ArrayList<>(List.of("POOL")));
        hotelSearchDTO.setSort("noSort");

        Hotel hotel = new Hotel();
        HotelViewDTO hotelViewDTO = new HotelViewDTO();
        declareHotelAndHotelView(comforts, hotel, hotelViewDTO);

        this.testHotels.add(hotel);
        this.testHotelViews.add(hotelViewDTO);
        this.hotelsNotDistinct.add(hotel);
        this.noStarHotels.add(hotel);
        this.noStarHotelNotDistinct.add(hotel);

        Mockito.when(this.mockedHotelRepository.findDistinctAllByAccommodationInAndCategoryInAndComfortsIn(accommodations, categories, comforts))
                .thenReturn(this.testHotels);
        Mockito.when(this.mockedHotelRepository.findAllByAccommodationInAndCategoryInAndComfortsIn(accommodations, categories, comforts))
                .thenReturn(this.hotelsNotDistinct);
        Mockito.when(this.mockedHotelRepository.findDistinctAllByAccommodationInAndCategoryIsNullAndComfortsIn(accommodations, comforts))
                .thenReturn(this.noStarHotels);
        Mockito.when(this.mockedHotelRepository.findAllByAccommodationInAndCategoryIsNullAndComfortsIn(accommodations, comforts))
                .thenReturn(this.noStarHotelNotDistinct);
        List<HotelViewDTO> allByNameAndAccommodationAndCategoryAndComfort = this.hotelService.getAllByNameAndAccommodationAndCategoryAndComfort(hotelSearchDTO);
        allByNameAndAccommodationAndCategoryAndComfort.addAll(this.testHotelViews);

        String expectedName = this.mockedHotelRepository.findDistinctAllByAccommodationInAndCategoryInAndComfortsIn(accommodations, categories, comforts).get(0).getName();
        String actualName = allByNameAndAccommodationAndCategoryAndComfort.get(0).getName();

        Assertions.assertEquals(expectedName, actualName);
    }

    @Test
    public void getAllByNameAndAccommodationAndCategoryAndComfort_nameEmptyAndTownNullAndComfortsEmpty() {
        List<Accommodation> accommodations = new ArrayList<>();
        List<HotelCategory> categories = new ArrayList<>();
        List<Comfort> comforts = new ArrayList<>();

        HotelSearchDTO hotelSearchDTO = new HotelSearchDTO();
        hotelSearchDTO.setName("");
        hotelSearchDTO.setAccommodation(new ArrayList<>(List.of("ALL_INCLUSIVE")));
        hotelSearchDTO.setCategory(new ArrayList<>());
        hotelSearchDTO.setTownName("noTown");
        hotelSearchDTO.setComfort(new ArrayList<>());
        hotelSearchDTO.setSort("noSort");

        Hotel hotel = new Hotel();
        HotelViewDTO hotelViewDTO = new HotelViewDTO();
        declareHotelAndHotelView(comforts, hotel, hotelViewDTO);

        this.testHotels.add(hotel);
        this.testHotelViews.add(hotelViewDTO);
        this.hotelsNotDistinct.add(hotel);
        this.noStarHotels.add(hotel);

        Mockito.when(this.mockedHotelRepository.findDistinctAllByAccommodationInAndCategoryIn(accommodations, categories))
                .thenReturn(this.testHotels);
        Mockito.when(this.mockedHotelRepository.findAllByAccommodationInAndCategoryIn(accommodations, categories))
                .thenReturn(this.hotelsNotDistinct);
        Mockito.when(this.mockedHotelRepository.findAllByAccommodationInAndCategoryIsNull(accommodations))
                .thenReturn(this.noStarHotels);
        List<HotelViewDTO> allByNameAndAccommodationAndCategoryAndComfort = this.hotelService.getAllByNameAndAccommodationAndCategoryAndComfort(hotelSearchDTO);
        allByNameAndAccommodationAndCategoryAndComfort.addAll(this.testHotelViews);

        String expectedName = this.mockedHotelRepository.findDistinctAllByAccommodationInAndCategoryIn(accommodations, categories).get(0).getName();
        String actualName = allByNameAndAccommodationAndCategoryAndComfort.get(0).getName();

        Assertions.assertEquals(expectedName, actualName);
    }

    @Test
    public void getAllByNameAndAccommodationAndCategoryAndComfort_nameEmptyAndTownNotNullAndComfortsNotEmpty() {
        List<Accommodation> accommodations = new ArrayList<>();
        List<HotelCategory> categories = new ArrayList<>();
        List<Comfort> comforts = this.mockedComfortRepository.findAll();

        HotelSearchDTO hotelSearchDTO = new HotelSearchDTO();
        hotelSearchDTO.setName("");
        hotelSearchDTO.setAccommodation(new ArrayList<>(List.of("ALL_INCLUSIVE")));
        hotelSearchDTO.setCategory(new ArrayList<>());
        hotelSearchDTO.setTownName("KITEN");
        hotelSearchDTO.setComfort(new ArrayList<>(List.of("POOL")));
        hotelSearchDTO.setSort("noSort");

        Hotel hotel = new Hotel();
        HotelViewDTO hotelViewDTO = new HotelViewDTO();
        declareHotelAndHotelView(comforts, hotel, hotelViewDTO);
        Town town = new Town();
        town.setId(1);
        town.setName(TownEnum.KITEN);
        Mockito.when(this.mockedTownRepository.findByName(TownEnum.KITEN))
                .thenReturn(town);
        Town townFromRepo = this.mockedTownRepository.findByName(TownEnum.KITEN);
        hotel.setTown(townFromRepo);
        TownDTO townDTO = new TownDTO();
        townDTO.setName(hotel.getTown().getName());
        hotelViewDTO.setTown(townDTO);

        this.testHotels.add(hotel);
        this.testHotelViews.add(hotelViewDTO);
        this.hotelsNotDistinct.add(hotel);
        this.noStarHotels.add(hotel);
        this.noStarHotelNotDistinct.add(hotel);

        Mockito.when(this.mockedHotelRepository
                        .findDistinctAllByTownAndAccommodationInAndCategoryInAndComfortsIn(town, accommodations, categories, comforts))
                .thenReturn(this.testHotels);
        Mockito.when(this.mockedHotelRepository.findAllByTownAndAccommodationInAndCategoryInAndComfortsIn(town, accommodations, categories, comforts))
                .thenReturn(this.hotelsNotDistinct);
        Mockito.when(this.mockedHotelRepository.findDistinctAllByTownAndAccommodationInAndCategoryIsNullAndComfortsIn(town, accommodations, comforts))
                .thenReturn(this.noStarHotels);
        Mockito.when(this.mockedHotelRepository.findAllByTownAndAccommodationInAndCategoryIsNullAndComfortsIn(town, accommodations, comforts))
                .thenReturn(this.noStarHotelNotDistinct);
        List<HotelViewDTO> allByNameAndAccommodationAndCategoryAndComfort = this.hotelService.getAllByNameAndAccommodationAndCategoryAndComfort(hotelSearchDTO);
        allByNameAndAccommodationAndCategoryAndComfort.addAll(this.testHotelViews);

        String expectedName = this.mockedHotelRepository
                .findDistinctAllByTownAndAccommodationInAndCategoryInAndComfortsIn(town, accommodations, categories, comforts)
                .get(0).getName();
        String actualName = allByNameAndAccommodationAndCategoryAndComfort.get(0).getName();
        Assertions.assertEquals(expectedName, actualName);
    }

    @Test
    public void getAllByNameAndAccommodationAndCategoryAndComfort_nameEmptyAndTownNotNullAndComfortsEmpty() {
        List<Accommodation> accommodations = new ArrayList<>();
        List<HotelCategory> categories = new ArrayList<>();
        List<Comfort> comforts = new ArrayList<>();

        HotelSearchDTO hotelSearchDTO = new HotelSearchDTO();
        hotelSearchDTO.setName("");
        hotelSearchDTO.setAccommodation(new ArrayList<>(List.of("ALL_INCLUSIVE")));
        hotelSearchDTO.setCategory(new ArrayList<>());
        hotelSearchDTO.setTownName("KITEN");
        hotelSearchDTO.setComfort(new ArrayList<>());
        hotelSearchDTO.setSort("noSort");

        Hotel hotel = new Hotel();
        HotelViewDTO hotelViewDTO = new HotelViewDTO();
        declareHotelAndHotelView(comforts, hotel, hotelViewDTO);
        Town town = new Town();
        town.setId(1);
        town.setName(TownEnum.KITEN);
        Mockito.when(this.mockedTownRepository.findByName(TownEnum.KITEN))
                .thenReturn(town);
        Town townFromRepo = this.mockedTownRepository.findByName(TownEnum.KITEN);
        hotel.setTown(townFromRepo);
        TownDTO townDTO = new TownDTO();
        townDTO.setName(hotel.getTown().getName());
        hotelViewDTO.setTown(townDTO);

        this.testHotels.add(hotel);
        this.testHotelViews.add(hotelViewDTO);
        this.hotelsNotDistinct.add(hotel);
        this.noStarHotels.add(hotel);

        Mockito.when(this.mockedHotelRepository
                        .findDistinctAllByTownAndAccommodationInAndCategoryIn(town, accommodations, categories))
                .thenReturn(this.testHotels);
        Mockito.when(this.mockedHotelRepository.findAllByTownAndAccommodationInAndCategoryIn(town, accommodations, categories))
                .thenReturn(this.hotelsNotDistinct);
        Mockito.when(this.mockedHotelRepository.findAllByTownAndAccommodationInAndCategoryIsNull(town, accommodations))
                .thenReturn(this.noStarHotels);
        List<HotelViewDTO> allByNameAndAccommodationAndCategoryAndComfort = this.hotelService.getAllByNameAndAccommodationAndCategoryAndComfort(hotelSearchDTO);
        allByNameAndAccommodationAndCategoryAndComfort.addAll(this.testHotelViews);

        String expectedName = this.mockedHotelRepository
                .findDistinctAllByTownAndAccommodationInAndCategoryIn(town, accommodations, categories)
                .get(0).getName();
        String actualName = allByNameAndAccommodationAndCategoryAndComfort.get(0).getName();
        Assertions.assertEquals(expectedName, actualName);
    }



    //Name Not Empty
    @Test
    public void getAllByNameAndAccommodationAndCategoryAndComfort_nameNotEmptyAndTownNullAndComfortsNotEmpty() {
        List<Accommodation> accommodations = new ArrayList<>();
        List<HotelCategory> categories = new ArrayList<>();
        List<Comfort> comforts = this.mockedComfortRepository.findAll();

        HotelSearchDTO hotelSearchDTO = new HotelSearchDTO();
        hotelSearchDTO.setName("Hotel Neva");
        hotelSearchDTO.setAccommodation(new ArrayList<>(List.of("ALL_INCLUSIVE")));
        hotelSearchDTO.setCategory(new ArrayList<>());
        hotelSearchDTO.setTownName("noTown");
        hotelSearchDTO.setComfort(new ArrayList<>(List.of("POOL")));
        hotelSearchDTO.setSort("noSort");

        Hotel hotel = new Hotel();
        HotelViewDTO hotelViewDTO = new HotelViewDTO();
        declareHotelAndHotelView(comforts, hotel, hotelViewDTO);

        this.testHotels.add(hotel);
        this.testHotelViews.add(hotelViewDTO);
        this.hotelsNotDistinct.add(hotel);
        this.noStarHotels.add(hotel);
        this.noStarHotelNotDistinct.add(hotel);


        Mockito.when(this.mockedHotelRepository.findDistinctAllByNameAndAccommodationInAndCategoryInAndComfortsIn("Hotel Neva", accommodations, categories, comforts))
                .thenReturn(this.testHotels);
        Mockito.when(this.mockedHotelRepository.findAllByNameAndAccommodationInAndCategoryInAndComfortsIn(hotelSearchDTO.getName(), accommodations, categories, comforts))
                .thenReturn(this.hotelsNotDistinct);
        Mockito.when(this.mockedHotelRepository.findDistinctAllByNameAndAccommodationInAndCategoryIsNullAndComfortsIn(hotelSearchDTO.getName(), accommodations, comforts))
                .thenReturn(this.noStarHotels);
        Mockito.when(this.mockedHotelRepository.findAllByNameAndAccommodationInAndCategoryIsNullAndComfortsIn(hotelSearchDTO.getName(), accommodations, comforts))
                .thenReturn(this.noStarHotelNotDistinct);
        List<HotelViewDTO> allByNameAndAccommodationAndCategoryAndComfort = this.hotelService.getAllByNameAndAccommodationAndCategoryAndComfort(hotelSearchDTO);
        allByNameAndAccommodationAndCategoryAndComfort.addAll(this.testHotelViews);

        String expectedName = this.mockedHotelRepository.findDistinctAllByNameAndAccommodationInAndCategoryInAndComfortsIn("Hotel Neva", accommodations, categories, comforts).get(0).getName();
        String actualName = allByNameAndAccommodationAndCategoryAndComfort.get(0).getName();

        Assertions.assertEquals(expectedName, actualName);
    }

    @Test
    public void getAllByNameAndAccommodationAndCategoryAndComfort_nameNotEmptyAndTownNullAndComfortsEmpty() {
        List<Accommodation> accommodations = new ArrayList<>();
        List<HotelCategory> categories = new ArrayList<>();
        List<Comfort> comforts = new ArrayList<>();

        HotelSearchDTO hotelSearchDTO = new HotelSearchDTO();
        hotelSearchDTO.setName("Hotel Neva");
        hotelSearchDTO.setAccommodation(new ArrayList<>(List.of("ALL_INCLUSIVE")));
        hotelSearchDTO.setCategory(new ArrayList<>());
        hotelSearchDTO.setTownName("noTown");
        hotelSearchDTO.setComfort(new ArrayList<>());
        hotelSearchDTO.setSort("noSort");

        Hotel hotel = new Hotel();
        HotelViewDTO hotelViewDTO = new HotelViewDTO();
        declareHotelAndHotelView(comforts, hotel, hotelViewDTO);

        this.testHotels.add(hotel);
        this.testHotelViews.add(hotelViewDTO);
        this.hotelsNotDistinct.add(hotel);
        this.noStarHotels.add(hotel);

        Mockito.when(this.mockedHotelRepository.findDistinctAllByNameAndAccommodationInAndCategoryIn("Hotel Neva", accommodations, categories))
                .thenReturn(this.testHotels);
        Mockito.when(this.mockedHotelRepository.findAllByNameAndAccommodationInAndCategoryIn(hotelSearchDTO.getName(), accommodations, categories))
                .thenReturn(this.hotelsNotDistinct);
        Mockito.when(this.mockedHotelRepository.findAllByNameAndAccommodationInAndCategoryIsNull(hotelSearchDTO.getName(), accommodations))
                .thenReturn(this.noStarHotels);
        List<HotelViewDTO> allByNameAndAccommodationAndCategoryAndComfort = this.hotelService.getAllByNameAndAccommodationAndCategoryAndComfort(hotelSearchDTO);
        allByNameAndAccommodationAndCategoryAndComfort.addAll(this.testHotelViews);

        String expectedName = this.mockedHotelRepository.findDistinctAllByNameAndAccommodationInAndCategoryIn("Hotel Neva", accommodations, categories).get(0).getName();
        String actualName = allByNameAndAccommodationAndCategoryAndComfort.get(0).getName();

        Assertions.assertEquals(expectedName, actualName);
    }

    @Test
    public void getAllByNameAndAccommodationAndCategoryAndComfort_nameNotEmptyAndTownNotNullAndComfortsNotEmpty() {
        List<Accommodation> accommodations = new ArrayList<>();
        List<HotelCategory> categories = new ArrayList<>();
        List<Comfort> comforts = this.mockedComfortRepository.findAll();

        HotelSearchDTO hotelSearchDTO = new HotelSearchDTO();
        hotelSearchDTO.setName("Hotel Neva");
        hotelSearchDTO.setAccommodation(new ArrayList<>(List.of("ALL_INCLUSIVE")));
        hotelSearchDTO.setCategory(new ArrayList<>());
        hotelSearchDTO.setTownName("KITEN");
        hotelSearchDTO.setComfort(new ArrayList<>(List.of("POOL")));
        hotelSearchDTO.setSort("noSort");

        Hotel hotel = new Hotel();
        HotelViewDTO hotelViewDTO = new HotelViewDTO();
        declareHotelAndHotelView(comforts, hotel, hotelViewDTO);
        Town town = new Town();
        town.setId(1);
        town.setName(TownEnum.KITEN);
        Mockito.when(this.mockedTownRepository.findByName(TownEnum.KITEN))
                .thenReturn(town);
        Town townFromRepo = this.mockedTownRepository.findByName(TownEnum.KITEN);
        hotel.setTown(townFromRepo);
        TownDTO townDTO = new TownDTO();
        townDTO.setName(hotel.getTown().getName());
        hotelViewDTO.setTown(townDTO);

        this.testHotels.add(hotel);
        this.testHotelViews.add(hotelViewDTO);
        this.hotelsNotDistinct.add(hotel);
        this.noStarHotels.add(hotel);
        this.noStarHotelNotDistinct.add(hotel);

        Mockito.when(this.mockedHotelRepository
                        .findDistinctAllByNameAndTownAndAccommodationInAndCategoryInAndComfortsIn("Hotel Neva", town, accommodations, categories, comforts))
                .thenReturn(this.testHotels);
        Mockito.when(this.mockedHotelRepository
                        .findAllByNameAndTownAndAccommodationInAndCategoryInAndComfortsIn(hotelSearchDTO.getName(), town, accommodations, categories, comforts))
                .thenReturn(this.hotelsNotDistinct);
        Mockito.when(this.mockedHotelRepository
                        .findDistinctAllByNameAndTownAndAccommodationInAndCategoryIsNullAndComfortsIn(hotelSearchDTO.getName(), town, accommodations, comforts))
                .thenReturn(this.noStarHotels);
        Mockito.when(this.mockedHotelRepository.findAllByNameAndTownAndAccommodationInAndCategoryIsNullAndComfortsIn(hotelSearchDTO.getName(), town, accommodations, comforts))
                .thenReturn(this.noStarHotelNotDistinct);
        List<HotelViewDTO> allByNameAndAccommodationAndCategoryAndComfort = this.hotelService.getAllByNameAndAccommodationAndCategoryAndComfort(hotelSearchDTO);
        allByNameAndAccommodationAndCategoryAndComfort.addAll(this.testHotelViews);

        String expectedName = this.mockedHotelRepository
                .findDistinctAllByNameAndTownAndAccommodationInAndCategoryInAndComfortsIn("Hotel Neva", town, accommodations, categories, comforts)
                .get(0).getName();
        String actualName = allByNameAndAccommodationAndCategoryAndComfort.get(0).getName();
        Assertions.assertEquals(expectedName, actualName);
    }

    @Test
    public void getAllByNameAndAccommodationAndCategoryAndComfort_nameNotEmptyAndTownNotNullAndComfortsEmpty() {
        List<Accommodation> accommodations = new ArrayList<>();
        List<HotelCategory> categories = new ArrayList<>();
        List<Comfort> comforts = new ArrayList<>();

        HotelSearchDTO hotelSearchDTO = new HotelSearchDTO();
        hotelSearchDTO.setName("Hotel Neva");
        hotelSearchDTO.setAccommodation(new ArrayList<>(List.of("ALL_INCLUSIVE")));
        hotelSearchDTO.setCategory(new ArrayList<>());
        hotelSearchDTO.setTownName("KITEN");
        hotelSearchDTO.setComfort(new ArrayList<>());
        hotelSearchDTO.setSort("noSort");

        Hotel hotel = new Hotel();
        HotelViewDTO hotelViewDTO = new HotelViewDTO();
        declareHotelAndHotelView(comforts, hotel, hotelViewDTO);
        Town town = new Town();
        town.setId(1);
        town.setName(TownEnum.KITEN);
        Mockito.when(this.mockedTownRepository.findByName(TownEnum.KITEN))
                .thenReturn(town);
        Town townFromRepo = this.mockedTownRepository.findByName(TownEnum.KITEN);
        hotel.setTown(townFromRepo);
        TownDTO townDTO = new TownDTO();
        townDTO.setName(hotel.getTown().getName());
        hotelViewDTO.setTown(townDTO);

        this.testHotels.add(hotel);
        this.testHotelViews.add(hotelViewDTO);
        this.hotelsNotDistinct.add(hotel);
        this.noStarHotels.add(hotel);

        Mockito.when(this.mockedHotelRepository
                        .findDistinctAllByNameAndTownAndAccommodationInAndCategoryIn("Hotel Neva", town, accommodations, categories))
                .thenReturn(this.testHotels);
        Mockito.when(this.mockedHotelRepository.findAllByNameAndTownAndAccommodationInAndCategoryIn(hotelSearchDTO.getName(), town, accommodations, categories))
                .thenReturn(this.hotelsNotDistinct);
        Mockito.when(this.mockedHotelRepository.findAllByNameAndTownAndAccommodationInAndCategoryIsNull(hotelSearchDTO.getName(), town, accommodations))
                .thenReturn(this.noStarHotels);
        List<HotelViewDTO> allByNameAndAccommodationAndCategoryAndComfort = this.hotelService.getAllByNameAndAccommodationAndCategoryAndComfort(hotelSearchDTO);
        allByNameAndAccommodationAndCategoryAndComfort.addAll(this.testHotelViews);

        String expectedName = this.mockedHotelRepository
                .findDistinctAllByNameAndTownAndAccommodationInAndCategoryIn("Hotel Neva", town, accommodations, categories)
                .get(0).getName();
        String actualName = allByNameAndAccommodationAndCategoryAndComfort.get(0).getName();
        Assertions.assertEquals(expectedName, actualName);
    }

    @Test
    void testGetAllBySea() {
        List<Comfort> comforts = new ArrayList<>();
        Hotel hotel = new Hotel();
        HotelViewDTO hotelViewDTO = new HotelViewDTO();
        declareHotelAndHotelView(comforts, hotel, hotelViewDTO);
        this.testHotels.add(hotel);
        Mockito.when(this.mockedHotelRepository.findAllByTownRegionTypeRegion(RegionEnum.SEA))
                .thenReturn(this.testHotels);
        List<HotelViewDTO> allBySea = this.hotelService.getAllBySea();
        Assertions.assertEquals(this.testHotels.get(0).getName(), allBySea.get(0).getName());
    }

    @Test
    void testGetAllByMountain() {
        List<Comfort> comforts = new ArrayList<>();
        Hotel hotel = new Hotel();
        HotelViewDTO hotelViewDTO = new HotelViewDTO();
        declareHotelAndHotelView(comforts, hotel, hotelViewDTO);
        this.testHotels.add(hotel);
        Mockito.when(this.mockedHotelRepository.findAllByTownRegionTypeRegion(RegionEnum.MOUNTAIN))
                .thenReturn(this.testHotels);
        List<HotelViewDTO> allBySea = this.hotelService.getAllByMountain();
        Assertions.assertEquals(this.testHotels.get(0).getName(), allBySea.get(0).getName());
    }

    @Test
    void testGetAllByAllInclusive() {
        List<Comfort> comforts = new ArrayList<>();
        Hotel hotel = new Hotel();
        HotelViewDTO hotelViewDTO = new HotelViewDTO();
        declareHotelAndHotelView(comforts, hotel, hotelViewDTO);
        this.testHotels.add(hotel);
        Mockito.when(this.mockedHotelRepository.findAllByAccommodation(Accommodation.ALL_INCLUSIVE))
                .thenReturn(this.testHotels);
        List<HotelViewDTO> allByAllInclusive = this.hotelService.getAllByAllInclusive();
        Assertions.assertEquals(this.testHotels.get(0).getName(), allByAllInclusive.get(0).getName());
    }


    private void declareHotelAndHotelView(List<Comfort> comforts, Hotel hotel, HotelViewDTO hotelViewDTO) {
        hotel.setName("Hotel Neva");
        hotel.setInformation("Just some information");
        hotel.setCategory(HotelCategory.FIVE_STAR);
        hotel.setAccommodation(Accommodation.ALL_INCLUSIVE);
        hotel.setImages(new ArrayList<>(List.of("www.photo.com")));
        hotel.setComforts(comforts);
        hotel.setOwner(new User());
        hotel.setTown(new Town());

        hotelViewDTO.setName("Hotel Neva");
        hotelViewDTO.setInformation("Just some information");
        hotelViewDTO.setCategory(HotelCategory.FIVE_STAR);
        hotelViewDTO.setAccommodation(Accommodation.ALL_INCLUSIVE);
        hotelViewDTO.setImages(new ArrayList<>(List.of("www.photo.com")));
        List<ComfortDTO> comfortDTOs = new ArrayList<>();
        comforts.forEach(c -> {
            ComfortDTO comfortDTO = new ComfortDTO();
            comfortDTO.setName(c.getName());
            comfortDTOs.add(comfortDTO);
        });
        hotelViewDTO.setComforts(comfortDTOs);
    }
}
