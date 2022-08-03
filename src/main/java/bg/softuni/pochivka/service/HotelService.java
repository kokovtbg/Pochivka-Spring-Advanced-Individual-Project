package bg.softuni.pochivka.service;

import bg.softuni.pochivka.model.dto.*;
import bg.softuni.pochivka.model.entity.*;
import bg.softuni.pochivka.model.enums.*;
import bg.softuni.pochivka.model.mapper.HotelMapper;
import bg.softuni.pochivka.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HotelService {
    private final HotelRepository hotelRepository;
    private final ComfortRepository comfortRepository;
    private final TownRepository townRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final HotelMapper hotelMapper;

    @Autowired
    public HotelService(HotelRepository hotelRepository,
                        ComfortRepository comfortRepository,
                        TownRepository townRepository,
                        RoomRepository roomRepository,
                        UserRepository userRepository,
                        HotelMapper hotelMapper) {
        this.hotelRepository = hotelRepository;
        this.comfortRepository = comfortRepository;
        this.townRepository = townRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.hotelMapper = hotelMapper;
    }

    public List<HotelViewDTO> getAllByNameAndAccommodationAndCategoryAndComfort(HotelSearchDTO hotelDTO) {
        String name = hotelDTO.getName();
        String townName = hotelDTO.getTownName();
        List<String> accommodationList = hotelDTO.getAccommodation();
        List<String> categoryList = hotelDTO.getCategory();
        List<String> comfortList = hotelDTO.getComfort();
        String sort = hotelDTO.getSort();

        Town town = null;
        if (!townName.equals("noTown")) {
            TownEnum townEnum = TownEnum.valueOf(townName);
            town = this.townRepository.findByName(townEnum);
        }
        List<Accommodation> accommodations = new ArrayList<>();
        accommodationList.forEach(a -> {
            Accommodation accommodationEnum = Accommodation.valueOf(a);
            accommodations.add(accommodationEnum);
        });
        List<HotelCategory> categories = new ArrayList<>();
        categoryList.forEach(c -> {
            HotelCategory categoryEnum = HotelCategory.valueOf(c);
            categories.add(categoryEnum);
        });
        List<Comfort> comforts = new ArrayList<>();
        comfortList.forEach(c -> {
            ComfortEnum comfortEnum = ComfortEnum.valueOf(c);
            Comfort comfort = this.comfortRepository.findByName(comfortEnum);
            comforts.add(comfort);
        });

        List<Hotel> hotels;
        List<Hotel> hotelsNotDistinct;
        if (name.equals("")) {
            boolean haveToShowNoStarHotels = false;
            if (accommodations.size() == 0) {
                accommodations.addAll(Arrays.stream(Accommodation.values()).toList());
            }
            if (categories.size() == 0) {
                categories.addAll(Arrays.stream(HotelCategory.values()).toList());
                haveToShowNoStarHotels = true;
            }
            if (town == null) {
                if (comforts.size() != 0) {
                    hotels = this.hotelRepository
                            .findDistinctAllByAccommodationInAndCategoryInAndComfortsIn(accommodations, categories, comforts);
                    hotelsNotDistinct = this.hotelRepository
                            .findAllByAccommodationInAndCategoryInAndComfortsIn(accommodations, categories, comforts);
                    if (haveToShowNoStarHotels) {
                        List<Hotel> noStarHotels = this.hotelRepository
                                .findDistinctAllByAccommodationInAndCategoryIsNullAndComfortsIn(accommodations, comforts);
                        hotels.addAll(noStarHotels);
                        List<Hotel> noStarHotelsNotDistinct = this.hotelRepository
                                .findAllByAccommodationInAndCategoryIsNullAndComfortsIn(accommodations,comforts);
                        hotelsNotDistinct.addAll(noStarHotelsNotDistinct);
                    }
                } else {
                    hotels = this.hotelRepository
                            .findDistinctAllByAccommodationInAndCategoryIn(accommodations, categories);
                    hotelsNotDistinct = this.hotelRepository
                            .findAllByAccommodationInAndCategoryIn(accommodations, categories);
                    if (haveToShowNoStarHotels) {
                        List<Hotel> noStarHotels = this.hotelRepository
                                .findAllByAccommodationInAndCategoryIsNull(accommodations);
                        hotels.addAll(noStarHotels);
                        hotelsNotDistinct.addAll(noStarHotels);
                    }
                }
            } else {
                if (comforts.size() != 0) {
                    hotels = this.hotelRepository
                            .findDistinctAllByTownAndAccommodationInAndCategoryInAndComfortsIn(town, accommodations, categories, comforts);
                    hotelsNotDistinct = this.hotelRepository
                            .findAllByTownAndAccommodationInAndCategoryInAndComfortsIn(town, accommodations, categories, comforts);
                    if (haveToShowNoStarHotels) {
                        List<Hotel> noStarHotels = this.hotelRepository
                                .findDistinctAllByTownAndAccommodationInAndCategoryIsNullAndComfortsIn(town, accommodations, comforts);
                        hotels.addAll(noStarHotels);
                        List<Hotel> noStarHotelsNotDistinct = this.hotelRepository
                                .findAllByTownAndAccommodationInAndCategoryIsNullAndComfortsIn(town, accommodations,comforts);
                        hotelsNotDistinct.addAll(noStarHotelsNotDistinct);
                    }
                } else {
                    hotels = this.hotelRepository
                            .findDistinctAllByTownAndAccommodationInAndCategoryIn(town, accommodations, categories);
                    hotelsNotDistinct = this.hotelRepository
                            .findAllByTownAndAccommodationInAndCategoryIn(town, accommodations, categories);
                    if (haveToShowNoStarHotels) {
                        List<Hotel> noStarHotels = this.hotelRepository
                                .findAllByTownAndAccommodationInAndCategoryIsNull(town, accommodations);
                        hotels.addAll(noStarHotels);
                        hotelsNotDistinct.addAll(noStarHotels);
                    }
                }
            }


        } else {
            boolean haveToShowNoStarHotels = false;
            if (accommodations.size() == 0) {
                accommodations.addAll(Arrays.stream(Accommodation.values()).toList());
            }
            if (categories.size() == 0) {
                categories.addAll(Arrays.stream(HotelCategory.values()).toList());
                haveToShowNoStarHotels = true;
            }
            if (town == null) {
                if (comforts.size() != 0) {
                    hotels = this.hotelRepository
                            .findDistinctAllByNameAndAccommodationInAndCategoryInAndComfortsIn(name, accommodations, categories, comforts);
                    hotelsNotDistinct = this.hotelRepository
                            .findAllByNameAndAccommodationInAndCategoryInAndComfortsIn(name, accommodations, categories, comforts);
                    if (haveToShowNoStarHotels) {
                        List<Hotel> noStarHotels = this.hotelRepository
                                .findDistinctAllByNameAndAccommodationInAndCategoryIsNullAndComfortsIn(name, accommodations, comforts);
                        hotels.addAll(noStarHotels);
                        List<Hotel> noStarHotelsNotDistinct = this.hotelRepository
                                .findAllByNameAndAccommodationInAndCategoryIsNullAndComfortsIn(name, accommodations, comforts);
                        hotelsNotDistinct.addAll(noStarHotelsNotDistinct);
                    }
                } else {
                    hotels = this.hotelRepository
                            .findDistinctAllByNameAndAccommodationInAndCategoryIn(name, accommodations, categories);
                    hotelsNotDistinct = this.hotelRepository
                            .findAllByNameAndAccommodationInAndCategoryIn(name, accommodations, categories);
                    if (haveToShowNoStarHotels) {
                        List<Hotel> noStarHotels = this.hotelRepository
                                .findAllByNameAndAccommodationInAndCategoryIsNull(name, accommodations);
                        hotels.addAll(noStarHotels);
                        hotelsNotDistinct.addAll(noStarHotels);
                    }
                }
            } else {
                if (comforts.size() != 0) {
                    hotels = this.hotelRepository
                            .findDistinctAllByNameAndTownAndAccommodationInAndCategoryInAndComfortsIn(name, town, accommodations, categories, comforts);
                    hotelsNotDistinct = this.hotelRepository
                            .findAllByNameAndTownAndAccommodationInAndCategoryInAndComfortsIn(name, town, accommodations, categories, comforts);
                    if (haveToShowNoStarHotels) {
                        List<Hotel> noStarHotels = this.hotelRepository
                                .findDistinctAllByNameAndTownAndAccommodationInAndCategoryIsNullAndComfortsIn(name, town, accommodations, comforts);
                        hotels.addAll(noStarHotels);
                        List<Hotel> noStarHotelsNotDistinct = this.hotelRepository
                                .findAllByNameAndTownAndAccommodationInAndCategoryIsNullAndComfortsIn(name, town, accommodations, comforts);
                        hotelsNotDistinct.addAll(noStarHotelsNotDistinct);
                    }
                } else {
                    hotels = this.hotelRepository
                            .findDistinctAllByNameAndTownAndAccommodationInAndCategoryIn(name, town, accommodations, categories);
                    hotelsNotDistinct = this.hotelRepository
                            .findAllByNameAndTownAndAccommodationInAndCategoryIn(name, town, accommodations, categories);
                    if (haveToShowNoStarHotels) {
                        List<Hotel> noStarHotels = this.hotelRepository
                                .findAllByNameAndTownAndAccommodationInAndCategoryIsNull(name, town, accommodations);
                        hotels.addAll(noStarHotels);
                        hotelsNotDistinct.addAll(noStarHotels);
                    }
                }
            }

        }
        if (hotelsNotDistinct.size() != comforts.size() * hotels.size() && comforts.size() > 0) {
            List<Hotel> hotelsToRemove = new ArrayList<>();
            hotels.forEach(h -> {
                int count = 0;
                for (Comfort c : h.getComforts()) {
                    if (comforts.contains(c)) {
                        count++;
                    }
                }
                if (count != comforts.size()) {
                    hotelsToRemove.add(h);
                }
            });
            hotels.removeAll(hotelsToRemove);
        }

        List<HotelViewDTO> hotelViewDTOs = new ArrayList<>();
        for (Hotel hotel : hotels) {
            HotelViewDTO hotelViewDTO = new HotelViewDTO();
            this.hotelMapper.hotelToHotelViewDTO(hotel, hotelViewDTO);
            hotelViewDTOs.add(hotelViewDTO);
        }

        if (sort.equals("price")) {
            hotelViewDTOs = hotelViewDTOs.stream()
                    .sorted(Comparator.comparing(HotelViewDTO::getLowestPrice))
                    .collect(Collectors.toList());
        } else if (sort.equals("townName")) {
            hotelViewDTOs = hotelViewDTOs.stream()
                    .sorted(Comparator.comparing(h -> h.getTown().getName()))
                    .collect(Collectors.toList());
        }
        return hotelViewDTOs;
    }

    public HotelViewDTO findById(long id) {
        Optional<Hotel> hotelById = this.hotelRepository.findById(id);
        Hotel hotel;
        HotelViewDTO hotelViewDTO = null;
        if (hotelById.isPresent()) {
            hotel = hotelById.get();
            hotelViewDTO = new HotelViewDTO();
            this.hotelMapper.hotelToHotelViewDTO(hotel, hotelViewDTO);
        }
        return hotelViewDTO;
    }

    public void register(HotelAddDTO hotelAddDTO, String username) {
        Hotel hotel = new Hotel();
        hotel.setName(hotelAddDTO.getName());
        hotel.setWebsite(hotelAddDTO.getWebsite());
        hotel.setInformation(hotelAddDTO.getInformation());
        String hotelAddDTOCategory = hotelAddDTO.getCategory();
        HotelCategory category = null;
        if (!hotelAddDTOCategory.equals("noCategory")) {
            category = HotelCategory.valueOf(hotelAddDTOCategory);
        }
        hotel.setCategory(category);
        Accommodation accommodation = Accommodation.valueOf(hotelAddDTO.getAccommodation());
        hotel.setAccommodation(accommodation);
        List<String> images = Arrays.stream(hotelAddDTO.getImages().split("\n")).toList();
        hotel.setImages(images);
        List<Comfort> comforts = new ArrayList<>();
        hotelAddDTO.getComforts().forEach(c -> {
            ComfortEnum comfortEnum = ComfortEnum.valueOf(c);
            Comfort comfort = this.comfortRepository.findByName(comfortEnum);
            comforts.add(comfort);
        });
        hotel.setComforts(comforts);
        List<Room> rooms = new ArrayList<>();
        hotelAddDTO.getRooms().forEach(r -> {
            Room room = new Room();
            room.setRoomType(r.getRoomType());
            room.setSeason(r.getSeason());
            room.setPrice(r.getPrice());
            if (room.getPrice() != null) {
                room.setHotel(hotel);
                rooms.add(room);
            }
        });
        hotel.setRooms(rooms);
        Optional<User> optUserByUsername = this.userRepository.findByUsername(username);
        User user = null;
        if (optUserByUsername.isPresent()) {
            user = optUserByUsername.get();
        }
        hotel.setOwner(user);
        TownEnum townEnum = TownEnum.valueOf(hotelAddDTO.getTown());
        Town town = this.townRepository.findByName(townEnum);
        hotel.setTown(town);

        this.hotelRepository.save(hotel);
        this.roomRepository.saveAll(rooms);
    }

    public boolean hasRoomsAndAllPricesGreaterThanZero(HotelAddDTO hotelAddDTO) {
        List<RoomDTO> rooms = hotelAddDTO.getRooms();
        BigDecimal sum = new BigDecimal(0);
        for (RoomDTO room : rooms) {
            BigDecimal price = room.getPrice();
            if (price != null && price.compareTo(BigDecimal.ZERO) <= 0) {
                return false;
            }
            if (price != null) {
                sum = sum.add(price);
            }
        }

        return  sum.compareTo(BigDecimal.ZERO) > 0;
    }

    public List<HotelViewDTO> getAllBySea() {
        List<Hotel> hotels = this.hotelRepository.findAllByTownRegionTypeRegion(RegionEnum.SEA);
        List<HotelViewDTO> hotelViewDTOs = new ArrayList<>();
        hotels.forEach(h -> {
            HotelViewDTO hotelViewDTO = new HotelViewDTO();
            this.hotelMapper.hotelToHotelViewDTO(h, hotelViewDTO);
            hotelViewDTOs.add(hotelViewDTO);
        });
        return hotelViewDTOs;
    }

    public List<HotelViewDTO> getAllByMountain() {
        List<Hotel> hotels = this.hotelRepository.findAllByTownRegionTypeRegion(RegionEnum.MOUNTAIN);
        List<HotelViewDTO> hotelViewDTOs = new ArrayList<>();
        hotels.forEach(h -> {
            HotelViewDTO hotelViewDTO = new HotelViewDTO();
            this.hotelMapper.hotelToHotelViewDTO(h, hotelViewDTO);
            hotelViewDTOs.add(hotelViewDTO);
        });
        return hotelViewDTOs;
    }

    public List<HotelViewDTO> getAllByAllInclusive() {
        List<Hotel> hotels = this.hotelRepository.findAllByAccommodation(Accommodation.ALL_INCLUSIVE);
        List<HotelViewDTO> hotelViewDTOs = new ArrayList<>();
        hotels.forEach(h -> {
            HotelViewDTO hotelViewDTO = new HotelViewDTO();
            this.hotelMapper.hotelToHotelViewDTO(h, hotelViewDTO);
            hotelViewDTOs.add(hotelViewDTO);
        });
        return hotelViewDTOs;
    }
}
