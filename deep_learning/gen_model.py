import numpy as np
np.random.seed(100) # for reproducibility
import tensorflow as tf
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import LSTM, Dense, Dropout, BatchNormalization, Bidirectional, Flatten
from tensorflow.keras import regularizers

import time
from tensorflow.keras.callbacks import TensorBoard, ModelCheckpoint

BATCH_SIZE = 256
EPOCHS = 55

DATA_NAME = '9x9_220m_8000s'
VALID_DATA_NAME = '9x9_220m_8000s' + '_test.npz'
filepath = "9x9_220m_8000s-{epoch:02d}-{val_accuracy:.3f}"
NORM = 80.0
SIZE = 81

NAME = f'{DATA_NAME}-{int(time.time())}'

with np.load(f'./data/{DATA_NAME}.npz') as data:
    train_data = data['train_data']
    train_label = data['train_label']
    

with np.load(f'./data/{VALID_DATA_NAME}') as data:
    valid_data = data['train_data']
    valid_label = data['train_label']

train_data /= NORM
valid_data /= NORM

model = Sequential()
model.add(Bidirectional(LSTM(32, return_sequences=True, unroll=True),  input_shape=(1, SIZE)))
model.add(BatchNormalization())
model.add(Bidirectional(LSTM(32, unroll=True)))
model.add(BatchNormalization())
model.add(Dense(16, activation='relu'))
model.add(Dense(8, activation='relu'))
model.add(Dense(4, activation='softmax'))
model.compile(loss='sparse_categorical_crossentropy', optimizer='adam', metrics=['accuracy'])

tensorboard = TensorBoard(log_dir=f'logs/{NAME}')

checkpoint = ModelCheckpoint("models/{}.h5".format(filepath, monitor='val_accuracy', verbose=1, save_best_only=True, mode='max'))

model.fit(train_data, train_label, batch_size=BATCH_SIZE, epochs=EPOCHS, verbose=2, validation_data=(valid_data, valid_label), callbacks=[tensorboard, checkpoint])
